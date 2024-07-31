package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReviewReaction
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReviewReactionId
import java.util.UUID

interface GameReviewReactionRepository : JpaRepository<GameReviewReaction, GameReviewReactionId> {

    fun findByIdGameReviewIdAndIdMemberId(gameReviewId: Long, memberId: UUID): GameReviewReaction?

    @Query("select grr from GameReviewReaction grr join fetch grr.id.gameReview where grr.id.member.id = :memberId")
    fun findByMemberId(memberId: UUID): List<GameReviewReaction>

    fun deleteByIdGameReviewId(gameReviewId: Long)
}