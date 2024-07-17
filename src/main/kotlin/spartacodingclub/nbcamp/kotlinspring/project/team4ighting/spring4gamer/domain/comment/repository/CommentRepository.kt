package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment

interface CommentRepository : JpaRepository<Comment, Long> {

}