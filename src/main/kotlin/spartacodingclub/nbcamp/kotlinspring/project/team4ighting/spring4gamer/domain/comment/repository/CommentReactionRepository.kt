package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.CommentReaction
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.CommentReactionid
import java.util.UUID

interface CommentReactionRepository : JpaRepository<CommentReaction, CommentReactionid> {

    fun findByIdCommentIdAndIdMemberId(commentId: Long, memberId: UUID): CommentReaction?
}