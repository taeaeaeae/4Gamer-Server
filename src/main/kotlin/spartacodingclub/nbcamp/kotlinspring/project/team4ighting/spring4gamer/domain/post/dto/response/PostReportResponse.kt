package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.ReportStatus
import java.util.*

data class PostReportResponse (
    val id: Long,
    val postInfo: PostSimplifiedResponse,
    val reason: String,
    val subject: UUID,
    val status: ReportStatus
)