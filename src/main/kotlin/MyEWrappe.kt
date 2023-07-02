import com.ib.client.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference


class MyEWrapper() : EWrapper {

    val readerSignal = EJavaSignal()
    val clientSocket = EClientSocket(this, readerSignal)
    val finished = AtomicBoolean(false)
    val data: MutableList<HistoricalTickLast> = mutableListOf()
    val contract = AtomicReference<Contract>()

    override fun historicalTicksLast(reqId: Int, ticks: MutableList<HistoricalTickLast>?, done: Boolean) {
        if (ticks == null) { return }
        for (tick in ticks) {
            data.addAll(ticks)
            println(
                EWrapperMsgGenerator.historicalTickLast(
                    reqId, tick.time(), tick.tickAttribLast(), tick.price(), tick.size(), tick.exchange(),
                    tick.specialConditions()
                )
            )
        }

        finished.compareAndExchange(false, done)
    }

    override fun symbolSamples(i: Int, contractDescriptions: Array<out ContractDescription>?) {
        contractDescriptions?.forEach {cd->
            cd.derivativeSecTypes().forEach {
                println("Derivative description ${it}")
            }

        }

        contract.set(contractDescriptions?.first()?.contract())
    }

    override fun tickPrice(i: Int, i1: Int, v: Double, tickAttrib: TickAttrib?) {
        TODO("Not yet implemented")
    }

    override fun tickSize(i: Int, i1: Int, decimal: Decimal?) {
        TODO("Not yet implemented")
    }

    override fun tickOptionComputation(
        tickerId: Int,
        field: Int,
        tickAttrib: Int,
        impliedVol: Double,
        delta: Double,
        optPrice: Double,
        pvDividend: Double,
        gamma: Double,
        vega: Double,
        theta: Double,
        undPrice: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun tickGeneric(tickerId: Int, tickType: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun tickString(tickerId: Int, tickType: Int, value: String?) {
        TODO("Not yet implemented")
    }

    override fun tickEFP(
        tickerId: Int,
        tickType: Int,
        basisPoints: Double,
        formattedBasisPoints: String?,
        impliedFuture: Double,
        holdDays: Int,
        futureLastTradeDate: String?,
        dividendImpact: Double,
        dividendsToLastTradeDate: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun orderStatus(
        i: Int,
        s: String?,
        decimal: Decimal?,
        decimal1: Decimal?,
        v: Double,
        i1: Int,
        i2: Int,
        v1: Double,
        i3: Int,
        s1: String?,
        v2: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun openOrder(i: Int, contract: Contract?, order: Order?, orderState: OrderState?) {
        TODO("Not yet implemented")
    }

    override fun openOrderEnd() {
        TODO("Not yet implemented")
    }

    override fun updateAccountValue(key: String?, value: String?, currency: String?, accountName: String?) {
        TODO("Not yet implemented")
    }

    override fun updatePortfolio(
        contract: Contract?,
        decimal: Decimal?,
        v: Double,
        v1: Double,
        v2: Double,
        v3: Double,
        v4: Double,
        s: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun updateAccountTime(timeStamp: String?) {
        TODO("Not yet implemented")
    }

    override fun accountDownloadEnd(accountName: String?) {
        TODO("Not yet implemented")
    }

    override fun nextValidId(orderId: Int) {
        println("Next valid id ${orderId}")
    }

    override fun contractDetails(i: Int, contractDetails: ContractDetails?) {
        contract.set(contractDetails?.contract())
    }

    override fun bondContractDetails(i: Int, contractDetails: ContractDetails?) {
        TODO("Not yet implemented")
    }

    override fun contractDetailsEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun execDetails(i: Int, contract: Contract?, execution: Execution?) {
        TODO("Not yet implemented")
    }

    override fun execDetailsEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun updateMktDepth(i: Int, i1: Int, i2: Int, i3: Int, v: Double, decimal: Decimal?) {
        TODO("Not yet implemented")
    }

    override fun updateMktDepthL2(
        i: Int,
        i1: Int,
        s: String?,
        i2: Int,
        i3: Int,
        v: Double,
        decimal: Decimal?,
        b: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun updateNewsBulletin(msgId: Int, msgType: Int, message: String?, origExchange: String?) {
        TODO("Not yet implemented")
    }

    override fun managedAccounts(accountsList: String?) {
        println("Managed accounts: $accountsList")
    }

    override fun receiveFA(faDataType: Int, xml: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalData(i: Int, bar: Bar?) {
        TODO("Not yet implemented")
    }

    override fun scannerParameters(xml: String?) {
        TODO("Not yet implemented")
    }

    override fun scannerData(
        i: Int,
        i1: Int,
        contractDetails: ContractDetails?,
        s: String?,
        s1: String?,
        s2: String?,
        s3: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun scannerDataEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun realtimeBar(
        i: Int,
        l: Long,
        v: Double,
        v1: Double,
        v2: Double,
        v3: Double,
        decimal: Decimal?,
        decimal1: Decimal?,
        i1: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun currentTime(time: Long) {
        TODO("Not yet implemented")
    }

    override fun fundamentalData(reqId: Int, data: String?) {
        TODO("Not yet implemented")
    }

    override fun deltaNeutralValidation(i: Int, deltaNeutralContract: DeltaNeutralContract?) {
        TODO("Not yet implemented")
    }

    override fun tickSnapshotEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun marketDataType(reqId: Int, marketDataType: Int) {
        TODO("Not yet implemented")
    }

    override fun commissionReport(commissionReport: CommissionReport?) {
        TODO("Not yet implemented")
    }

    override fun position(s: String?, contract: Contract?, decimal: Decimal?, v: Double) {
        TODO("Not yet implemented")
    }

    override fun positionEnd() {
        TODO("Not yet implemented")
    }

    override fun accountSummary(reqId: Int, account: String?, tag: String?, value: String?, currency: String?) {
        TODO("Not yet implemented")
    }

    override fun accountSummaryEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun verifyMessageAPI(apiData: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyCompleted(isSuccessful: Boolean, errorText: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyAndAuthMessageAPI(apiData: String?, xyzChallenge: String?) {
        TODO("Not yet implemented")
    }

    override fun verifyAndAuthCompleted(isSuccessful: Boolean, errorText: String?) {
        TODO("Not yet implemented")
    }

    override fun displayGroupList(reqId: Int, groups: String?) {
        TODO("Not yet implemented")
    }

    override fun displayGroupUpdated(reqId: Int, contractInfo: String?) {
        TODO("Not yet implemented")
    }

    override fun error(e: Exception?) {
        TODO("Not yet implemented")
    }

    override fun error(str: String?) {
        TODO("Not yet implemented")
    }

    override fun error(id: Int, errorCode: Int, errorMsg: String?, advancedOrderRejectJson: String?) {
        if (id == -1) {
            println("Notification ${errorCode}")
            return
        }

        println("Error ${errorCode}")
    }

    override fun connectionClosed() {
        TODO("Not yet implemented")
    }

    override fun connectAck() {
        TODO("Not yet implemented")
    }

    override fun positionMulti(i: Int, s: String?, s1: String?, contract: Contract?, decimal: Decimal?, v: Double) {
        TODO("Not yet implemented")
    }

    override fun positionMultiEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun accountUpdateMulti(
        reqId: Int,
        account: String?,
        modelCode: String?,
        key: String?,
        value: String?,
        currency: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun accountUpdateMultiEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun securityDefinitionOptionalParameter(
        reqId: Int,
        exchange: String?,
        underlyingConId: Int,
        tradingClass: String?,
        multiplier: String?,
        expirations: MutableSet<String>?,
        strikes: MutableSet<Double>?
    ) {
        TODO("Not yet implemented")
    }

    override fun securityDefinitionOptionalParameterEnd(reqId: Int) {
        TODO("Not yet implemented")
    }

    override fun softDollarTiers(i: Int, softDollarTiers: Array<out SoftDollarTier>?) {
        TODO("Not yet implemented")
    }

    override fun familyCodes(familyCodes: Array<out FamilyCode>?) {
        TODO("Not yet implemented")
    }

    override fun historicalDataEnd(reqId: Int, startDateStr: String?, endDateStr: String?) {
        TODO("Not yet implemented")
    }

    override fun mktDepthExchanges(depthMktDataDescriptions: Array<out DepthMktDataDescription>?) {
        TODO("Not yet implemented")
    }

    override fun tickNews(
        tickerId: Int,
        timeStamp: Long,
        providerCode: String?,
        articleId: String?,
        headline: String?,
        extraData: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun smartComponents(reqId: Int, theMap: MutableMap<Int, MutableMap.MutableEntry<String, Char>>?) {
        TODO("Not yet implemented")
    }

    override fun tickReqParams(tickerId: Int, minTick: Double, bboExchange: String?, snapshotPermissions: Int) {
        TODO("Not yet implemented")
    }

    override fun newsProviders(newsProviders: Array<out NewsProvider>?) {
        TODO("Not yet implemented")
    }

    override fun newsArticle(requestId: Int, articleType: Int, articleText: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalNews(
        requestId: Int,
        time: String?,
        providerCode: String?,
        articleId: String?,
        headline: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun historicalNewsEnd(requestId: Int, hasMore: Boolean) {
        TODO("Not yet implemented")
    }

    override fun headTimestamp(reqId: Int, headTimestamp: String?) {
        TODO("Not yet implemented")
    }

    override fun histogramData(reqId: Int, items: MutableList<HistogramEntry>?) {
        TODO("Not yet implemented")
    }

    override fun historicalDataUpdate(i: Int, bar: Bar?) {
        TODO("Not yet implemented")
    }

    override fun rerouteMktDataReq(reqId: Int, conId: Int, exchange: String?) {
        TODO("Not yet implemented")
    }

    override fun rerouteMktDepthReq(reqId: Int, conId: Int, exchange: String?) {
        TODO("Not yet implemented")
    }

    override fun marketRule(i: Int, priceIncrements: Array<out PriceIncrement>?) {
        TODO("Not yet implemented")
    }

    override fun pnl(reqId: Int, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double) {
        TODO("Not yet implemented")
    }

    override fun pnlSingle(i: Int, decimal: Decimal?, v: Double, v1: Double, v2: Double, v3: Double) {
        TODO("Not yet implemented")
    }

    override fun historicalTicks(reqId: Int, ticks: MutableList<HistoricalTick>?, done: Boolean) {
        TODO("Not yet implemented")
    }

    override fun historicalTicksBidAsk(reqId: Int, ticks: MutableList<HistoricalTickBidAsk>?, done: Boolean) {
        TODO("Not yet implemented")
    }

    override fun tickByTickAllLast(
        i: Int,
        i1: Int,
        l: Long,
        v: Double,
        decimal: Decimal?,
        tickAttribLast: TickAttribLast?,
        s: String?,
        s1: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun tickByTickBidAsk(
        i: Int,
        l: Long,
        v: Double,
        v1: Double,
        decimal: Decimal?,
        decimal1: Decimal?,
        tickAttribBidAsk: TickAttribBidAsk?
    ) {
        TODO("Not yet implemented")
    }

    override fun tickByTickMidPoint(reqId: Int, time: Long, midPoint: Double) {
        TODO("Not yet implemented")
    }

    override fun orderBound(orderId: Long, apiClientId: Int, apiOrderId: Int) {
        TODO("Not yet implemented")
    }

    override fun completedOrder(contract: Contract?, order: Order?, orderState: OrderState?) {
        TODO("Not yet implemented")
    }

    override fun completedOrdersEnd() {
        TODO("Not yet implemented")
    }

    override fun replaceFAEnd(reqId: Int, text: String?) {
        TODO("Not yet implemented")
    }

    override fun wshMetaData(reqId: Int, dataJson: String?) {
        TODO("Not yet implemented")
    }

    override fun wshEventData(reqId: Int, dataJson: String?) {
        TODO("Not yet implemented")
    }

    override fun historicalSchedule(
        reqId: Int,
        startDateTime: String?,
        endDateTime: String?,
        timeZone: String?,
        sessions: MutableList<HistoricalSession>?
    ) {
        TODO("Not yet implemented")
    }

    override fun userInfo(reqId: Int, whiteBrandingId: String?) {
        TODO("Not yet implemented")
    }


}