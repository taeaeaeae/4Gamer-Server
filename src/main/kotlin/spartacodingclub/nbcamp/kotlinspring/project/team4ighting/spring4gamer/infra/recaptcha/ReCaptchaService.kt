package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.recaptcha

import com.fasterxml.jackson.databind.ObjectMapper
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
    private val objectMapper: ObjectMapper,
    @Value("\${google.client.secret}") private val secret: String,
) {

    private val recaptchaUrl = "https://www.google.com/recaptcha/api/siteverify"

    fun recaptcha(token: String?): RecaptchaResponse? {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)

        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("secret", secret)
        map.add("response", token)

        // 사용자 반응과 시크릿키를 request 에 담아서
        val request = HttpEntity(map, headers)

        // 구글 에이피아이에 포스트 요청 보내고
        val response = restTemplate.postForEntity(recaptchaUrl, request, String::class.java)

        // 응답 받아옴
        return response.body?.let {
            objectMapper.readValue(it, RecaptchaResponse::class.java)
        }
    }

}