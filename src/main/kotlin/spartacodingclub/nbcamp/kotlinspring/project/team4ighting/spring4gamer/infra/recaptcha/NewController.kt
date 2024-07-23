package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.recaptcha

import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class NewController(
    private val reCaptchaService: ReCaptchaService
) {

    @PostMapping("/validation")
    fun recaptcha(@RequestParam token: String?): ResponseEntity<RecaptchaResponse> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reCaptchaService.recaptcha(token))
    }
}