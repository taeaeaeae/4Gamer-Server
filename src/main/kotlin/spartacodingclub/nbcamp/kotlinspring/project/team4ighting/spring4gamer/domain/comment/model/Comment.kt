package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post
import java.util.*

@Entity
@Table(name = "comment")
class Comment private constructor(
    content: String,
    memberId: UUID,
    post: Post,
    author: String
) : BaseTimeEntity() {

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
    }
}

fun Comment.toResponse(): CommentResponse {

    return CommentResponse(id!!, content, author)
}