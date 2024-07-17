package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request.SigninRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.SigninResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request.SignupRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val authService: AuthService,
) {

    @PostMapping("/signin")
    fun signin(@RequestBody request: SigninRequest): ResponseEntity<SigninResponse> =
        ResponseEntity.status(HttpStatus.OK).body(authService.signin(request))


    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<MemberResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request))

}