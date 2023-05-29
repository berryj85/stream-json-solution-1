package berryj.poc.streamjson.composite.service

import berryj.poc.streamjson.composite.client.CoinServiceClient
import berryj.poc.streamjson.composite.client.CouponServiceClient
import berryj.poc.streamjson.composite.client.ProfileServiceClient
import berryj.poc.streamjson.composite.response.CoinResponse
import berryj.poc.streamjson.composite.response.ProfileResponse
import berryj.poc.streamjson.composite.response.TotalCouponResponse
import berryj.poc.streamjson.composite.response.UserProfileResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProfileService(
    val profileServiceClient: ProfileServiceClient,
    val coinServiceClient: CoinServiceClient,
    val couponServiceClient: CouponServiceClient
) {
    fun getProfile(accountId: String): Flux<ProfileResponse> {
        val profileResponse =
            profileServiceClient.getProfile(accountId).map { UserProfileResponse(email = it?.email, name = it?.name) }
        val coinResponse = coinServiceClient.getTotalCoin(accountId).map { CoinResponse(totalCoin = it?.totalCoin) }
        val couponResponse =
            couponServiceClient.getAllCoupon(accountId).collectList().map { TotalCouponResponse(it.size.toLong()) }

        return Flux.merge(profileResponse, coinResponse, couponResponse)
    }
}