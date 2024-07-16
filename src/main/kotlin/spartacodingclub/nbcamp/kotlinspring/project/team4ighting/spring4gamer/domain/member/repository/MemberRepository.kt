package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import java.util.UUID

interface MemberRepository : JpaRepository<Member, UUID> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Member?
    fun existsByNickname(nickname: String): Boolean
}