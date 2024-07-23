package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response

import java.io.Serializable
import java.time.ZonedDateTime

data class BoardResponse(
    val id: Long,
    val title: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
): Serializable