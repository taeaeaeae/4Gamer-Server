package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseReport
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostReportResponse

@Entity
@Table(name = "post_report")
class PostReport private constructor (
    post: Post,
    reason: String,
    subject: Member
) : BaseReport(
    reason = reason,
    subject = subject
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post = post


    companion object {

        fun from(post: Post, reason: String, subject: Member): PostReport =

            PostReport(
                post = post,
                reason = reason,
                subject = subject
            )
    }
}


fun PostReport.toResponse(): PostReportResponse =

    PostReportResponse(
        id = id!!,
        postInfo = post.toPostSimplifiedResponse(),
        reason = reason,
        subject = subject!!.id,
        status = status
    )