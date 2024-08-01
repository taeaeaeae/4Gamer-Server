package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.recaptcha

import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recaptcha")
class RecaptchaController(
    private val reCaptchaService: ReCaptchaService
) {

    @PostMapping
    fun recaptcha(@RequestParam token: String?): ResponseEntity<RecaptchaResponse> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reCaptchaService.recaptcha(token))
    }
}