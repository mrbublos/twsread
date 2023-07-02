import com.ib.client.ContractDescription
import com.ib.controller.ApiController
import java.util.concurrent.atomic.AtomicReference

class SymbolsHandler : ApiController.ISymbolSamplesHandler {

    val symbols = AtomicReference<Array<out ContractDescription>>()

    override fun symbolSamples(contractDescriptions: Array<out ContractDescription>?) {
        symbols.set(contractDescriptions)
    }
}