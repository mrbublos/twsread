package ext

import com.ib.client.HistoricalTick
import com.ib.client.HistoricalTickBidAsk
import com.ib.client.HistoricalTickLast
import com.ib.contracts.OptContract
import com.ib.controller.ApiController
import java.time.Instant
import java.util.concurrent.Semaphore

data class Option(
    val symbol: String,
    val exchange: String, val underlyingConId: Int, val tradingClass: String,
    val multiplier: String, val expiration: String, val strike: Double, val right: String
)

fun Option.loadHistoricalTrades(apiController: ApiController, from: String): Pair<List<Any>, String>  {
    val option = this
    println("Requesting trade for ${option}")
    val contract = OptContract(option.symbol, option.exchange, option.expiration, option.strike, option.right)
    val data = mutableListOf<HistoricalTickLast>()
    val semaphore = Semaphore(1).apply { acquire() }
    apiController.reqHistoricalTicks(contract, "${from} UTC", null, 1000, "TRADES", 0, true, object :
        ApiController.IHistoricalTickHandler {
        override fun historicalTick(reqId: Int, ticks: MutableList<HistoricalTick>?) {
        }

        override fun historicalTickBidAsk(reqId: Int, ticks: MutableList<HistoricalTickBidAsk>?) {
        }

        override fun historicalTickLast(reqId: Int, ticks: MutableList<HistoricalTickLast>?) {
            data.addAll(ticks ?: listOf())
            semaphore.release()
        }
    })

    semaphore.acquire()

    val lastDate = Instant.ofEpochSecond(data.last().time()).toString()
        .substring(0, 20)
        .replace("-", "")
        .replace("T", "")
    return data to lastDate

}