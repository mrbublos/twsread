package ext

import com.ib.client.Contract
import com.ib.client.Types
import com.ib.controller.ApiController
import com.ib.controller.ApiController.IHistoricalDataHandler
import com.ib.controller.Bar
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Semaphore


fun Contract.getBars(apiController: ApiController, from: ZonedDateTime): List<Bar> {
    val contract = this
    val allBars = ConcurrentLinkedQueue<Bar>()
    val semaphore = Semaphore(1).apply { acquire() }

    apiController.reqHistoricalData(
        contract,
        "", // till now
        2,
        Types.DurationUnit.YEAR,
        Types.BarSize._1_day,
        Types.WhatToShow.TRADES,
        true,
        false,
        object : IHistoricalDataHandler {
            override fun historicalData(bar: Bar?) {
                if (bar != null) {
                    allBars.add(bar)
                }
            }

            override fun historicalDataEnd() {
                semaphore.release()
            }
        }
    )

    semaphore.acquire()

    return allBars.sortedBy { it.timeStr() }.toList()
}

fun Contract.getOptionChain(
    apiController: ApiController,
): ConcurrentLinkedQueue<Option> {
    val contract = this
    val allOptions: ConcurrentLinkedQueue<Option> = ConcurrentLinkedQueue()
    val semaphore = Semaphore(1).apply { acquire() }

    apiController.reqSecDefOptParams(
        contract.symbol(),
        "",
        contract.secType, // TODO check
        contract.conid(),
        object : ApiController.ISecDefOptParamsReqHandler {
            override fun securityDefinitionOptionalParameter(
                exchange: String, underlyingConId: Int, tradingClass: String,
                multiplier: String, expirations: Set<String>, strikes: Set<Double>
            ) {
                println("")
                val options = expirations.map { expiration ->
                    strikes.map { strike ->
                        listOf(
                            Option(
                                contract.symbol(),
                                exchange,
                                underlyingConId,
                                tradingClass,
                                multiplier,
                                expiration,
                                strike,
                                "C"
                            ),
                            Option(
                                contract.symbol(),
                                exchange,
                                underlyingConId,
                                tradingClass,
                                multiplier,
                                expiration,
                                strike,
                                "P"
                            )
                        )
                    }.flatten()
                }
                allOptions.addAll(options.flatten())
            }

            override fun securityDefinitionOptionalParameterEnd(p0: Int) {
                semaphore.release()
            }
        })

    semaphore.acquire()

    return allOptions
}
