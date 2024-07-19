package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByPost(post: Post, pageable: Pageable): Page<Comment>

    fun findByIdAndPost(commentId: Long, post: Post): Comment?
    fun findAllByPostIdIn(postIds: Collection<Long>): List<Comment>

    fun findAllByPostId(postId: Long): List<Comment>
}