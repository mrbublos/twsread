import com.ib.controller.ApiController
import java.lang.Exception

class ConnectionHandler : ApiController.IConnectionHandler {
    override fun connected() {
        println("Connected")
    }

    override fun disconnected() {
        println("Disconnected")
    }

    override fun accountList(list: MutableList<String>?) {
    }

    override fun error(e: Exception?) {
        println("Error " + e?.message)
    }

    override fun message(id: Int, errorCode: Int, errorMsg: String?, advancedOrderRejectJson: String?) {
    }

    override fun show(string: String?) {
    }
}