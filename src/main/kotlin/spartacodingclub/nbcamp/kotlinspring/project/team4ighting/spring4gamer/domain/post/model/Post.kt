package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service.Board
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name = "post")
class Post private constructor(
    title: String,
    body: String,
    board: Board,
    memberId: Long,
    auther: String
) {
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

    @Column(name = "member_id", nullable = false)
    val memberId: Long = memberId

    val auther: String = auther

    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id", nullable = false)
//    val board: Board = board
    @Column(name = "board_id", nullable = false)
    val board: Long = 1L // TODO: Board 구현 후 수정해야 함

    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        private set

    companion object {

        fun from(request: CreatePostRequest, board: Board, memberId: Long, auther: String): Post {
            return Post(
                title = request.title,
                body = request.body,
                board = board,
                memberId = memberId,
                auther = auther
            )
        }
    }

    fun update(title: String, body: String) {
        this.title = title
        this.body = body
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(id!!, title, body, views, createdAt, updatedAt, auther, Board())
}