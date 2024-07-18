package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post

interface PostRepository : JpaRepository<Post, Long> {

    fun findByBoardId(boardId: Long, pageable: Pageable): Page<Post>

    fun findByIdAndBoardId(id: Long, boardId: Long): Post?
}