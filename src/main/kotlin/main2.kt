import com.ib.client.*
import com.ib.contracts.OptContract
import com.ib.controller.ApiController
import ext.getBars
import java.time.Instant
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Semaphore

fun main(args: Array<String>) {
    val apiController = ApiController(ConnectionHandler())

    apiController.connect("127.0.0.1", 7496, 1, null)

    val semaphore = Semaphore(1)
    var symbols: Array<ContractDescription>? = null;

    semaphore.acquire()
    println("Requesting symbol")
    apiController.reqMatchingSymbols(
        "VOO"
    ) {
        symbols = it
        semaphore.release()
    }

    semaphore.acquire()

    val contract = symbols?.first()?.contract()
    contract?.exchange("SMART")

    println("requesting historical data")
    val bars = contract?.getBars(apiController, ZonedDateTime.now().minusYears(2))

    apiController.disconnect()
}


