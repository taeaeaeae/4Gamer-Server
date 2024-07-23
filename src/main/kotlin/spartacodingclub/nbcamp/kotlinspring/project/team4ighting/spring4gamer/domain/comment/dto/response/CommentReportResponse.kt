package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.ReportStatus
import java.util.UUID

data class CommentReportResponse(
    val id: Long,
    val commentInfo: CommentResponse,
    val reason: String,
    val subject: UUID,
    val status: ReportStatus
)