import com.ib.client.*
import com.ib.controller.ApiController
import java.util.concurrent.Semaphore

fun main(args: Array<String>) {
    val apiController = ApiController(ConnectionHandler())

    val semaphore = Semaphore(1)
    var symbols: Array<ContractDescription>? = null;

    semaphore.acquire()
    println("Requesting symbol")
    apiController.reqMatchingSymbols(
        "VIX"
    ) {
        symbols = it
        semaphore.release()
    }

    semaphore.acquire()
    println("requesting trades")
    val contract = symbols?.first()?.contract()
    var data = mutableListOf<HistoricalTickLast>()
    apiController.reqHistoricalTicks(contract, "20220808 10:00:00 US/Eastern", null, 10, "TRADES", 1, true, object :
        ApiController.IHistoricalTickHandler {
        override fun historicalTick(reqId: Int, ticks: MutableList<HistoricalTick>?) {
            println("Tick " + ticks)
        }

        override fun historicalTickBidAsk(reqId: Int, ticks: MutableList<HistoricalTickBidAsk>?) {
            println("Tick bid ask " + ticks)
        }

        override fun historicalTickLast(reqId: Int, ticks: MutableList<HistoricalTickLast>?) {
            data.addAll(ticks ?: listOf())
            semaphore.release()
        }

    })

    semaphore.acquire()
    semaphore.acquire()

    apiController.disconnect()
}