package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.UpdateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewReportResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReview
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReviewReaction
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReviewReport
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewReactionRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewReportRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.UUID

@Service
class GameReviewService(
    private val gameReviewRepository: GameReviewRepository,
    private val memberRepository: MemberRepository,
    private val gameReviewReactionRepository: GameReviewReactionRepository,
    private val gameReviewReportRepository: GameReviewReportRepository
) {

    @Transactional
    fun createGameReview(
        request: CreateGameReviewRequest,
        memberId: UUID
    ): GameReviewResponse =

        gameReviewRepository.save(
            GameReview.from(
                request,
                memberId
            )
        ).toResponse()


    fun getGameReviewList(pageable: Pageable): Page<GameReviewResponse> =

        gameReviewRepository.findAll(pageable)
            .map { it.toResponse() }


    fun getGameReview(gameReviewId: Long): GameReviewResponse =

        gameReviewRepository.findByIdOrNull(gameReviewId)
            ?.toResponse()
            ?: throw ModelNotFoundException("GameReview", gameReviewId)


    @Transactional
    fun updateGameReview(
        gameReviewId: Long,
        request: UpdateGameReviewRequest,
        memberId: UUID
    ): GameReviewResponse {

        val targetGameReview = gameReviewRepository.findByIdOrNull(gameReviewId)
            ?: throw ModelNotFoundException("GameReview", gameReviewId)

        if (targetGameReview.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게임리뷰에 대한 수정 권한이 없습니다.")
        }

        targetGameReview.update(
            description = request.description,
            point = request.point
        )

        return gameReviewRepository.save(targetGameReview).toResponse()
    }


    @Transactional
    fun deleteGameReview(
        gameReviewId: Long,
        memberId: UUID
    ) {

        val targetGameReview = gameReviewRepository.findByIdOrNull(gameReviewId)
            ?: throw ModelNotFoundException("GameReview", gameReviewId)

        if (targetGameReview.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게임리뷰에 대한 삭제 권한이 없습니다.")
        }

        gameReviewRepository.delete(targetGameReview)
    }


    @Transactional
    fun addReaction(
        gameReviewId: Long,
        memberId: UUID,
        isUpvoting: Boolean
    ) {

        val targetGameReview = gameReviewRepository.findByIdOrNull(gameReviewId)
            ?: throw ModelNotFoundException("GameReview", gameReviewId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

        val reaction = gameReviewReactionRepository.findByIdGameReviewIdAndIdMemberId(gameReviewId, memberId)

        if (reaction == null) {
            val newReaction = GameReviewReaction.from(member, targetGameReview, isUpvoting)

            targetGameReview.increaseReaction(isUpvoting)
            gameReviewReactionRepository.save(newReaction)
        } else {
            targetGameReview.applySwitchedReaction(isUpvoting)
            reaction.isUpvoting = isUpvoting
        }
    }


    @Transactional
    fun deleteReaction(
        gameReviewId: Long,
        memberId: UUID
    ) {

        val targetGameReview = gameReviewRepository.findByIdOrNull(gameReviewId)
            ?: throw ModelNotFoundException("GameReview", gameReviewId)
        val reaction = gameReviewReactionRepository.findByIdGameReviewIdAndIdMemberId(gameReviewId, memberId)
            ?: throw ModelNotFoundException("GameReviewReaction", "${gameReviewId}/${memberId}")

        targetGameReview.decreaseReaction(reaction.isUpvoting)
        gameReviewReactionRepository.delete(reaction)
    }


    fun reportGameReview(
        gameReviewId: Long,
        memberId: UUID,
        reason: String
    ): GameReviewReportResponse {

        val targetGameReview = gameReviewRepository.findByIdOrNull(gameReviewId)
            ?: throw ModelNotFoundException("GameReview", gameReviewId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

        return gameReviewReportRepository.save(
            GameReviewReport.from(
                gameReview = targetGameReview,
                reason = reason,
                subject = member
            )
        ).toResponse()
    }


    fun getTopReviews(): List<GameReviewResponse> =

        gameReviewRepository.findTopGameReviews().map { it.toResponse() }
}