package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import java.time.ZonedDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val body: String,
    val views: Long, // 조회수
//    val upvoteCount: Int, // 추천
//    val downvoteCount: Int, // 비추천
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val author: String, // 닉네임
    val board: BoardResponse,
)