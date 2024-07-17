package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SigninRequest(
    @field: Email
    val email: String,
    @field: NotBlank
    val password: String
)