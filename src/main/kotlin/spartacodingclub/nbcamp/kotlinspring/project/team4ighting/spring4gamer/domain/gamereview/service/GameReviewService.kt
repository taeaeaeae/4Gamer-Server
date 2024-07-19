package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.UpdateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReview
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.UUID

@Service
class GameReviewService(
    private val gameReviewRepository: GameReviewRepository
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
}