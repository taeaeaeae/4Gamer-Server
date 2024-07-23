package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.controller

import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service.ReCaptchaService

@RestController
@RequestMapping("/login")
class NewController(
    private val reCaptchaService: ReCaptchaService
) {

    @PostMapping("/validation")
    fun recaptcha(token: String?): ResponseEntity<String> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reCaptchaService.recaptcha(token))
    }
}