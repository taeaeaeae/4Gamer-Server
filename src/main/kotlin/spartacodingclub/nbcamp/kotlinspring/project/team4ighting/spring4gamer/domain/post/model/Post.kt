package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostSimplifiedResponse
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    val board: Board = board


    companion object {

        fun from(
            request: CreatePostRequest,
            board: Board,
            memberId: UUID,
            author: String
        ): Post =

            Post(
                title = request.title,
                body = request.body,
                board = board,
                memberId = memberId,
                author = author
            )
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

fun Post.toResponse(): PostResponse =

    PostResponse(
        id = id!!,
        title = title,
        body = body,
        views = views,
        createdAt = createdAt,
        updatedAt = updatedAt,
        author = author,
        board = board.toResponse()
    )


fun Post.toPostSimplifiedResponse(): PostSimplifiedResponse =

    PostSimplifiedResponse(
        id = id!!,
        title = title,
        view = views,
        author = author,
        createdAt = createdAt
    )