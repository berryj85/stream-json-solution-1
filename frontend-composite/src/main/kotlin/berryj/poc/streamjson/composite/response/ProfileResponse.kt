package berryj.poc.streamjson.composite.response

import berryj.poc.streamjson.composite.constant.ResponseStatus

abstract class ProfileResponse(val statusCode: String,val namespace:String)
data class CoinResponse(val totalCoin: Long? = null) : ProfileResponse(ResponseStatus.SUCCESS.code,"coin-service")
data class UserProfileResponse(val email: String? = null, val name: String? = null) : ProfileResponse(ResponseStatus.SUCCESS.code,"profile-service")
data class TotalCouponResponse(val totalCoupon:Long? = null) : ProfileResponse(ResponseStatus.SUCCESS.code,"coupon-service")