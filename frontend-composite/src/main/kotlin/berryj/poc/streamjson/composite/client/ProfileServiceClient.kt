package berryj.poc.streamjson.composite.client

import berryj.poc.streamjson.composite.response.GetProfileResponse
import berryj.poc.streamjson.composite.response.ProfileServiceResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class ProfileServiceClient(private val profileServiceWebClient: WebClient) {
    companion object{
        val log: Logger = LogManager.getLogger(ProfileServiceClient::class.java)
    }
    fun getProfile(accountId: String): Mono<GetProfileResponse?> {
        return profileServiceWebClient.get().uri("/{account-id}", accountId).exchangeToMono {
            it.bodyToMono(object :
                ParameterizedTypeReference<ProfileServiceResponse<GetProfileResponse>>() {}).map { response ->
                if (it.statusCode().is2xxSuccessful && response.statusCode == "200") {
                    response.data
                } else {
                    throw Exception()
                }
            }

        }.doOnSuccess {
            log.info("End GetProfile :{}",it)
        }
    }
}