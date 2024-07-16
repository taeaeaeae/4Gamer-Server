package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.UUID


data class MemberPrincipal(
    val id: UUID,
    val name: String,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: String, email: String, roles: Set<String>) : this(
        UUID.fromString(id),
        email,
        roles.map { SimpleGrantedAuthority("ROLE_$it") })
}