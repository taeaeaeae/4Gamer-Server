package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity
@Table(name = "game_review_reaction")
class GameReviewReaction private constructor (
    member: Member,
    gameReview: GameReview,
    isUpvoting: Boolean
) {

    @EmbeddedId
    val id: GameReviewReactionId = GameReviewReactionId(
        member = member,
        gameReview = gameReview
    )

    @Column(name = "is_upvoting")
    var isUpvoting: Boolean = isUpvoting


    companion object {

        fun from(member: Member, gameReview: GameReview, isUpvoting: Boolean): GameReviewReaction =

            GameReviewReaction(
                member = member,
                gameReview = gameReview,
                isUpvoting = isUpvoting
            )
    }
}