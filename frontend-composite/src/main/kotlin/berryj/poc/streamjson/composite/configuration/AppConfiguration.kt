package berryj.poc.streamjson.composite.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class AppConfiguration {

    var couponService: ServiceConfiguration = ServiceConfiguration()
    var profileService: ServiceConfiguration = ServiceConfiguration()
    var coinService: ServiceConfiguration = ServiceConfiguration()

    data class ServiceConfiguration(var baseUrl: String = "")

}
