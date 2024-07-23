package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response

import java.util.*

data class MessageResponse(
    val subjectId: UUID,
    val targetId: UUID,
    val message: String
)
