package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.PostReaction
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.PostReactionId
import java.util.*

interface PostReactionRepository : JpaRepository<PostReaction, PostReactionId> {

    fun findByIdPostIdAndIdMemberId(postId: Long, memberId: UUID): PostReaction?
}