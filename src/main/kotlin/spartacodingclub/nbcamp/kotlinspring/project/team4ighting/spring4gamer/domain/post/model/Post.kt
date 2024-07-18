package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service.Board
import java.util.*

@Entity
@Table(name = "post")
class Post private constructor(
    title: String,
    body: String,
    board: Board,
    memberId: UUID,
    author: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title", nullable = false)
    var title: String = title
        private set

    @Column(name = "body", nullable = false)
    var body: String = body
        private set

    @Column(name = "views", nullable = false)
    var views: Long = 0
        private set

    @Column(name = "member_id", nullable = false)
    val memberId: UUID = memberId

    val author: String = author

    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id", nullable = false)
//    val board: Board = board
    @Column(name = "board_id", nullable = false)
    val board: Long = 1L // TODO: Board 구현 후 수정해야 함

    companion object {

        fun from(
            request: CreatePostRequest,
            board: Board,
            memberId: UUID,
            author: String
        )
                : Post {

            return Post(
                title = request.title,
                body = request.body,
                board = board,
                memberId = memberId,
                author = author
            )
        }
    }

    fun update(
        title: String,
        body: String
    ) {

        this.title = title
        this.body = body
    }

    fun updateViews() {
        this.views += 1
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(id!!, title, body, views, createdAt, updatedAt, author, Board()) // TODO: Board 구현 후 수정해야 함
}

fun Post.toPostSimplifiedResponse(): PostSimplifiedResponse {
    return PostSimplifiedResponse(id!!, title, views, author, createdAt)
}