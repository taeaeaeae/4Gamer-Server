package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.ReportStatus
import java.util.UUID

data class GameReviewReportResponse(
    val id: Long,
    val gameReviewInfo: GameReviewResponse,
    val reason: String,
    val subject: UUID,
    val status: ReportStatus
)