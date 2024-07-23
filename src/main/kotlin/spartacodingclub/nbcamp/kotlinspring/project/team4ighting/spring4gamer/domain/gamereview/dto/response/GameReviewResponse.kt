package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response

import java.time.ZonedDateTime

data class GameReviewResponse(
    val id: Long,
    val gameTitle: String,
    val point: Byte,
    val description: String,
    val upvotes: Long,
    val downvotes: Long,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)