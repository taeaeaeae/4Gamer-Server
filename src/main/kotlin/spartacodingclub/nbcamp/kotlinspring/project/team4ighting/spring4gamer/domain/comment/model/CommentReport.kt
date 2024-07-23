package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.response.CommentReportResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseReport
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity
@Table(name = "comment_report")
class CommentReport private constructor(
    comment: Comment,
    reason: String,
    subject: Member
) : BaseReport(
    reason = reason,
    subject = subject
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    val comment: Comment = comment


    companion object {

        fun from(comment: Comment, reason: String, subject: Member): CommentReport =

            CommentReport(
                comment = comment,
                reason = reason,
                subject = subject
            )
    }
}


fun CommentReport.toResponse(): CommentReportResponse =

    CommentReportResponse(
        id = id!!,
        commentInfo = comment.toResponse(),
        reason = reason,
        subject = subject!!.id,
        status = status
    )