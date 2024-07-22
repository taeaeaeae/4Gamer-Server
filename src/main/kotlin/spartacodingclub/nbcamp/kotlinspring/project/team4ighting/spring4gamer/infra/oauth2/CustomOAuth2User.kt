package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.OAuth2Response


class CustomOAuth2User(
    private val oAuth2Response: OAuth2Response,
    private val role: String
) : OAuth2User {
    override fun getAttributes(): Map<String, Any>? {
        return null
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val collection: MutableCollection<GrantedAuthority> = ArrayList()

        collection.add(GrantedAuthority { role })

        return collection
    }

    fun email(): String {
        return oAuth2Response.email
    }

    override fun getName(): String {
        return oAuth2Response.name
    }

    val username: String
        get() = oAuth2Response.provider + " " + oAuth2Response.providerId
}