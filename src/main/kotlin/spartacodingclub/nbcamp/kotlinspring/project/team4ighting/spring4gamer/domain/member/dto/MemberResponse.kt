package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto

import java.util.UUID

data class MemberResponse(
    val id: UUID,
    val email: String,
    val nickname: String
)