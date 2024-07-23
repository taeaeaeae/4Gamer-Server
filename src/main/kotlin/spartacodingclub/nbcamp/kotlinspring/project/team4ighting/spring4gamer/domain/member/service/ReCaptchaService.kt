package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class ReCaptchaService(
    private val restTemplate: RestTemplate,
    @Value("\${google.client.secret}") private val secret: String,
) {

    private val recaptchaUrl = "https://www.google.com/recaptcha/api/siteverify"

    fun recaptcha(token: String?): String? {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)

        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("secret", secret)
        map.add("response", token)

        val request = HttpEntity(map, headers)
        val response = restTemplate.postForEntity(recaptchaUrl, request, String::class.java)

        return response.body
    }

}