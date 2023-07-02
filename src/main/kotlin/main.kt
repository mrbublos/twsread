import com.ib.client.Contract
import com.ib.client.EReader
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val wrapper = MyEWrapper()
    val m_client = wrapper.clientSocket;
    m_client.eConnect("127.0.0.1", 7497, 2);

    val reader = EReader(m_client, wrapper.readerSignal)

    reader.start()

    val r = thread(true) {
        while (m_client.isConnected && !wrapper.finished.get()) {
            wrapper.readerSignal.waitForSignal()
            println("Got message")
            try {
                reader.processMsgs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val ticker = "IBKR"
    m_client.reqMatchingSymbols(1, ticker);

    var counter = 0
    while (wrapper.contract.get() == null && counter++ < 30) {
        println("Waiting for contract details ${counter}")
        Thread.sleep(1000)
    }

    val contract = wrapper.contract.get()

    m_client.reqHistoricalTicks(2, contract, "20220808 10:00:00 US/Eastern", null, 10, "TRADES", 1, true, null);

    r.join()

    Files.write(
        Paths.get("/tmp/${ticker}-${System.currentTimeMillis()}.csv"),
        wrapper.data.map { it.toString() },
        StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE
    )
}