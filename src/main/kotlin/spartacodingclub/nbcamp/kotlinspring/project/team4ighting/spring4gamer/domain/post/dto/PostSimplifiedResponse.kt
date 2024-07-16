package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto

import java.time.ZonedDateTime

data class PostSimplifiedResponse(
    val id: Long,
    val title: String,
    val view: Long,
    val author: String,
    val createdAt: ZonedDateTime
)