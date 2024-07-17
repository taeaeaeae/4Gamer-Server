package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByPostId(postId: Long, pageable: Pageable): Page<Comment>

    fun findByIdAndPostId(commentId: Long, postId: Long): Comment?
}