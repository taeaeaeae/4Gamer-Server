package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import jakarta.transaction.Transactional
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.SigninRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.SigninResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.SignupRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.JwtHelper

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtHelper: JwtHelper
) {

    @Transactional
    fun signup(request: SignupRequest): MemberResponse {
        if (memberRepository.existsByEmail(request.email)) throw IllegalArgumentException("이미 가입된 이메일")
        if (memberRepository.existsByNickname(request.nickname)) throw IllegalArgumentException("이미 존재하는 닉네임")
        return Member.from(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname,
        ).let { memberRepository.save(it) }
            .let { MemberResponse.from(it) }
    }

    fun signin(request: SigninRequest): SigninResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw IllegalArgumentException("회원정보를 찾을 수 없습니다.")
        if (!passwordEncoder.matches(request.password, member.password)
        ) throw IllegalArgumentException("password가 일치하지 않습니다.")
        return SigninResponse(
            jwtHelper.generateAccessToken(
                subject = member.id.toString(),
                email = member.email,
                role = MemberRole.USER.name
            )
        )
    }

}