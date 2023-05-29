package berryj.poc.streamjson.composite.response

import java.math.BigDecimal

data class CouponServiceResponse<T>(val statusCode: String, val statusMessage: String, val data: T? = null)
data class CouponDetail(val couponId: String, val displayName: String, val couponType: String,val couponValue:BigDecimal)