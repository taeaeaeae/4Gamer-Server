package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewResponse
import java.util.*

@Entity
@Table(name = "game_review")
class GameReview private constructor(
    gameTitle: String,
    point: Byte,
    description: String,
    memberId: UUID
) : BaseTimeEntity() {

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
    val memberId: UUID = memberId


    companion object {

        fun from(
            request: CreateGameReviewRequest,
            memberId: UUID
        ): GameReview =

            GameReview(
                gameTitle = request.gameTitle,
                description = request.description,
                point = request.point,
                memberId = memberId
            )
    }


    fun update(
        description: String,
        point: Byte
    ) {

        this.description = description
        this.point = point
    }
}

fun GameReview.toResponse(): GameReviewResponse =

    GameReviewResponse(
        id = id!!,
        gameTitle = gameTitle,
        point = point,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )