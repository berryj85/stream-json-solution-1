package berryj.poc.streamjson.composite.controller

import berryj.poc.streamjson.composite.response.CommonProfileResponse
import berryj.poc.streamjson.composite.service.ProfileService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/frontend-composite/profile")
class ProfileController (private val profileService: ProfileService){

    @GetMapping("/{account-id}", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun getProfile(@PathVariable("account-id", required = true) accountId:String):Flux<CommonProfileResponse>{
        return profileService.getProfile(accountId)
    }

}