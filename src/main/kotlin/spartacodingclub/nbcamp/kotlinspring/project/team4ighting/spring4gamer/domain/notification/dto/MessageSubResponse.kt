package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.dto

import java.util.*

data class MessageSubResponse(
    val subjectId: UUID,
    val targetId: UUID,
    val message: String
)