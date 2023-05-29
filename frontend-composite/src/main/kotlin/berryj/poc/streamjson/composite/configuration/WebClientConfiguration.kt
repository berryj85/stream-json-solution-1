package berryj.poc.streamjson.composite.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.LoopResources

@Configuration
class WebClientConfiguration(private val appConfiguration: AppConfiguration) {

    private fun buildHttpClient(threadPrefixName:String): HttpClient {
        return SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build().let { sslContext ->
                HttpClient.create().secure { t -> t.sslContext(sslContext) }
            }.runOn(LoopResources.create(threadPrefixName,10,true).onClient(true))
    }

    @Bean
    fun snakeCaseObjectMapper(): ObjectMapper = jacksonObjectMapper().also {
        it.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }


    @Bean
    fun snakeCaseExchangeStrategies(snakeCaseObjectMapper: ObjectMapper): ExchangeStrategies =
        ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(snakeCaseObjectMapper))
                configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(snakeCaseObjectMapper))
            }
            .build()

    @Bean
    fun couponServiceWebClient(snakeCaseExchangeStrategies: ExchangeStrategies): WebClient {
        return WebClient.builder()
            .baseUrl(appConfiguration.couponService.baseUrl)
            .exchangeStrategies(snakeCaseExchangeStrategies)
            .clientConnector(ReactorClientHttpConnector(buildHttpClient("CouponService-Client")))
            .build()
    }

    @Bean
    fun coinServiceWebClient(snakeCaseExchangeStrategies: ExchangeStrategies): WebClient {
        return WebClient.builder()
            .baseUrl(appConfiguration.coinService.baseUrl)
            .exchangeStrategies(snakeCaseExchangeStrategies)
            .clientConnector(ReactorClientHttpConnector(buildHttpClient("CoinService-Client")))
            .build()
    }

    @Bean
    fun profileServiceWebClient(snakeCaseExchangeStrategies: ExchangeStrategies): WebClient {
        return WebClient.builder()
            .baseUrl(appConfiguration.profileService.baseUrl)
            .exchangeStrategies(snakeCaseExchangeStrategies)
            .clientConnector(ReactorClientHttpConnector(buildHttpClient("ProfileService-Client")))

            .build()
    }
}