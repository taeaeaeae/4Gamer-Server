package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "comment")
class Comment private constructor(
    content: String,
    memberId: UUID,
    post: Post,
    author: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "content", nullable = false)
    var content: String = content
        private set

    @Column(name = "member_id", nullable = false)
    val memberId: UUID = memberId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post = post

    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        private set

    val author: String = author

    companion object {

        fun from(
            content: String,
            memberId: UUID,
            post: Post,
            author: String
        ): Comment {

            return Comment(
                content = content,
                memberId = memberId,
                post = post,
                author = author
            )
        }
    }

    fun update(content: String) {

        this.content = content
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}

fun Comment.toResponse(): CommentResponse {

    return CommentResponse(id!!, content, author)
}