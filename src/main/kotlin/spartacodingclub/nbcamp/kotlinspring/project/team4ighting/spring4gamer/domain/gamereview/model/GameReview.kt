package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.GameReviewResponse
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name = "game_review")
class GameReview private constructor(
    gameTitle: String,
    point: Byte,
    description: String,
    memberId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "game_title", nullable = false)
    val gameTitle = gameTitle

    @Column(name = "description", nullable = false)
    var description: String = description
        private set

    @Column(name = "point", nullable = false)
    var point: Byte = point
        private set

    @Column(name = "member_id", nullable = false)
    val memberId: Long = memberId

    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        private set

    companion object {

        fun from(request: CreateGameReviewRequest, memberId: Long): GameReview {
            return GameReview(
                gameTitle = request.gameTitle,
                description = request.description,
                point = request.point,
                memberId = memberId
            )
        }
    }

    fun update(description: String, point: Byte) {
        this.description = description
        this.point = point
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    // TODO: 이미지 업로드
}

fun GameReview.toResponse(): GameReviewResponse {
    return GameReviewResponse(id!!, gameTitle, point, description, createdAt, updatedAt)
}