package berryj.poc.streamjson.composite.response

data class CoinServiceResponse<T>(val statusCode: String, val statusMessage: String, val data: T? = null)
data class GetTotalCoinResponse(val totalCoin: Long? = null)