package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.response

import java.time.ZonedDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val author: String,
//    val upvoteCount: Int,
//    val downvoteCount: Int
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)