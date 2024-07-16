package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto

import java.time.ZonedDateTime

data class GameReviewResponse(
    val id: Long,
    val gameTitle: String,
    val point: Byte,
    val description: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)