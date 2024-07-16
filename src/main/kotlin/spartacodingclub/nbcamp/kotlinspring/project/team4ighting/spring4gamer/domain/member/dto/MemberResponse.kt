package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import java.util.UUID

data class MemberResponse(
    val id: UUID,
    val email: String,
    val nickname: String
) {
    companion object {
        fun from(member: Member): MemberResponse {
            return MemberResponse(member.id!!, member.email, member.nickname)
        }
    }
}