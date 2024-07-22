package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.OAuth2Response
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import java.util.UUID


@Service
class OAuth2Service(
    private val memberRepository: MemberRepository,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val oAuth2User = super.loadUser(userRequest)
        val oAuth2Response = OAuth2Response(oAuth2User.attributes)
        val username = oAuth2Response.provider + " " + oAuth2Response.providerId
        val existData = memberRepository.findByEmail(oAuth2Response.email)
        val role: String

        if (existData == null) {
            val member = Member.from(
                email = oAuth2Response.email,
                password = "",
                nickname = username
            )
            memberRepository.save(member)
            role = "ROLE_${member.role.name}"
        } else {
            role = "ROLE_${existData.role.name}"
        }

        return CustomOAuth2User(oAuth2Response, role)
    }
}