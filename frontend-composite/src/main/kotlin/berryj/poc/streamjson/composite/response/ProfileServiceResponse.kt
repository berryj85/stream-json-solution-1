package berryj.poc.streamjson.composite.response

data class ProfileServiceResponse<T>(val statusCode: String, val statusMessage: String, val data: T? = null)
data class GetProfileResponse(val accountId: String, val email: String, val name: String)