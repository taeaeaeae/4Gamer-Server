package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseReport
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewReportResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity
@Table(name = "game_review_report")
class GameReviewReport private constructor (
    gameReview: GameReview,
    reason: String,
    subject: Member
) : BaseReport(
    reason = reason,
    subject = subject
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_review_id", nullable = false)
    val gameReview: GameReview = gameReview


    companion object {

        fun from(gameReview: GameReview, reason: String, subject: Member): GameReviewReport =

            GameReviewReport(
                gameReview = gameReview,
                reason = reason,
                subject = subject
            )
    }
}


fun GameReviewReport.toResponse(): GameReviewReportResponse =

    GameReviewReportResponse(
        id = id!!,
        gameReviewInfo = gameReview.toResponse(),
        reason = reason,
        subject = subject!!.id,
        status = status
    )