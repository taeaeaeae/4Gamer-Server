package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
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
    userId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val gameTitle = gameTitle
    var description: String = description
    var point: Byte = point

    val userId: Long = userId

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        private set

    companion object {

        fun from(request: CreateGameReviewRequest, userId: Long): GameReview {
            return GameReview(
                gameTitle = request.gameTitle,
                description = request.description,
                point = request.point,
                userId = userId
            )
        }
    }

    // TODO: description, point 수정 메서드
    // TODO: 이미지 업로드
}

fun GameReview.toResponse(): GameReviewResponse {
    return GameReviewResponse(id!!, gameTitle, point, description, createdAt, updatedAt)
}