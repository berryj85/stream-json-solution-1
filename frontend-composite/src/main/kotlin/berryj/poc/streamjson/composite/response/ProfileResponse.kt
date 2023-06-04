package berryj.poc.streamjson.composite.response

import berryj.poc.streamjson.composite.constant.ResponseStatus
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class CommonProfileResponse(val statusCode: String,val namespace:String)
data class CoinResponse(val totalCoin: Long? = null, val status:ResponseStatus = ResponseStatus.SUCCESS) : CommonProfileResponse(status.code,"coin-service")
data class UserProfileResponse(val email: String? = null, val name: String? = null, val status:ResponseStatus = ResponseStatus.SUCCESS) : CommonProfileResponse(status.code,"profile-service")
data class TotalCouponResponse(val totalCoupon:Long? = null, val status:ResponseStatus = ResponseStatus.SUCCESS) : CommonProfileResponse(status.code,"coupon-service")