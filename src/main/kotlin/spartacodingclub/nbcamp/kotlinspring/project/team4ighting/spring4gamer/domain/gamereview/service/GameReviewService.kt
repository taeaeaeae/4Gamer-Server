package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.UpdateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReview
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException

@Service
class GameReviewService(
    private val gameReviewRepository: GameReviewRepository
) {

    @Transactional
    fun createGameReview(request: CreateGameReviewRequest, memberId: Long): GameReviewResponse {
        return gameReviewRepository.save(
            GameReview.from(
                request,
                memberId
            )
        ).toResponse()
    }

    fun getGameReviewList(pageable: Pageable): Page<GameReviewResponse> {
        return gameReviewRepository.findAll(pageable).map { it.toResponse() }
    }

    fun getGameReview(gameReviewId: Long): GameReviewResponse {
        val gameReview =
            gameReviewRepository.findByIdOrNull(gameReviewId) ?: throw ModelNotFoundException("GameReview", gameReviewId)
        return gameReview.toResponse()
    }

    @Transactional
    fun updateGameReview(gameReviewId: Long, request: UpdateGameReviewRequest, memberId: Long): GameReviewResponse {
        val gameReview =
            gameReviewRepository.findByIdOrNull(gameReviewId) ?: throw ModelNotFoundException("GameReview", gameReviewId)

        if (gameReview.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게임리뷰에 대한 수정 권한이 없습니다.")
        }

        gameReview.update(description = request.description, point = request.point)

        return gameReview.toResponse()
    }

    @Transactional
    fun deleteGameReview(gameReviewId: Long, memberId: Long) {
        val gameReview =
            gameReviewRepository.findByIdOrNull(gameReviewId) ?: throw ModelNotFoundException("GameReview", gameReviewId)

        if (gameReview.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게임리뷰에 대한 삭제 권한이 없습니다.")
        }

        gameReviewRepository.delete(gameReview)
    }
}