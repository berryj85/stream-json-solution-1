package berryj.poc.streamjson.composite.client

import berryj.poc.streamjson.composite.response.CouponDetail
import berryj.poc.streamjson.composite.response.CouponServiceResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class CouponServiceClient(val couponServiceWebClient: WebClient) {
    companion object{
        val log: Logger = LogManager.getLogger(CouponServiceClient::class.java)
    }
    fun getAllCoupon(accountId: String): Flux<CouponDetail?> {
        return couponServiceWebClient.get().uri("/{account-id}", accountId).exchangeToFlux {
            it.bodyToFlux(object :
                ParameterizedTypeReference<CouponServiceResponse<List<CouponDetail>>>() {}).flatMap { response ->
                if (it.statusCode().is2xxSuccessful && response.statusCode == "200") {
                    Flux.fromArray(response.data?.toTypedArray() ?: arrayOf<CouponDetail>())
                } else {
                    Flux.error(java.lang.Exception())
                }
            }

        }.doOnComplete {
            log.info("End GetAllCoupon :{}")
        }
    }
}