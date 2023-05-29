package berryj.poc.streamjson.composite.client

import berryj.poc.streamjson.composite.response.CoinServiceResponse
import berryj.poc.streamjson.composite.response.GetTotalCoinResponse
import org.apache.logging.log4j.LogManager
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class CoinServiceClient(private val coinServiceWebClient:WebClient) {
    companion object{
        val log = LogManager.getLogger(CoinServiceClient::class.java)
    }
    fun getTotalCoin(accountId:String): Mono<GetTotalCoinResponse?> {
        return coinServiceWebClient.get().uri("/{account-id}", accountId).exchangeToMono {
            it.bodyToMono(object :
                ParameterizedTypeReference<CoinServiceResponse<GetTotalCoinResponse>>() {}).map { response ->
                if (it.statusCode().is2xxSuccessful && response.statusCode == "200") {
                    response.data
                } else {
                    throw Exception()
                }
            }

        }.doOnSuccess {
            log.info("End GetTotalCoin :{}",it)
        }
    }
}