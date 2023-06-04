package berryj.poc.streamjson.composite.service

import berryj.poc.streamjson.composite.client.CoinServiceClient
import berryj.poc.streamjson.composite.client.CouponServiceClient
import berryj.poc.streamjson.composite.client.ProfileServiceClient
import berryj.poc.streamjson.composite.constant.ResponseStatus
import berryj.poc.streamjson.composite.response.CoinResponse
import berryj.poc.streamjson.composite.response.CommonProfileResponse
import berryj.poc.streamjson.composite.response.TotalCouponResponse
import berryj.poc.streamjson.composite.response.UserProfileResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProfileService(
    val profileServiceClient: ProfileServiceClient,
    val coinServiceClient: CoinServiceClient,
    val couponServiceClient: CouponServiceClient
) {
    fun getProfile(accountId: String): Flux<CommonProfileResponse> {
        val profileResponse =  profileServiceClient.getProfile(accountId).map { UserProfileResponse(email = it?.email, name = it?.name) }.onErrorResume {
                    Mono.just(UserProfileResponse(status = ResponseStatus.UNEXPECTED_ERROR))
        }
        val coinResponse = coinServiceClient.getTotalCoin(accountId).map { CoinResponse(totalCoin = it?.totalCoin) }.onErrorResume {
                Mono.just(CoinResponse(status = ResponseStatus.UNEXPECTED_ERROR))
        }
        val couponResponse =  couponServiceClient.getAllCoupon(accountId).collectList().map { TotalCouponResponse(it.size.toLong()) } .onErrorResume {
                    Mono.just(TotalCouponResponse(status = ResponseStatus.UNEXPECTED_ERROR))
        }

        return Flux.merge(profileResponse, coinResponse, couponResponse)
    }
}