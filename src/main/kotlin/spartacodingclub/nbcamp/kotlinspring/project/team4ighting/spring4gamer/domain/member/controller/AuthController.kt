package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request.SigninRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.SigninResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request.SignupRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service.AuthService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.CustomOAuth2User

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val authService: AuthService,
) {

    @PostMapping("/signin")
    fun signin(@RequestBody request: SigninRequest): ResponseEntity<SigninResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.signin(request))


    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<MemberResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authService.signup(request))


    @GetMapping("/signin/google")
    fun googleSignup(@AuthenticationPrincipal principal: CustomOAuth2User): ResponseEntity<SigninResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.googleSignin(principal.email()))
}