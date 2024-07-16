package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReview
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewRepository

@Service
class GameReviewService(
    private val gameReviewRepository: GameReviewRepository
) {

    fun createGameReview(request: CreateGameReviewRequest, userId: Long): GameReviewResponse {
        return gameReviewRepository.save(
            GameReview.from(
                request,
                userId
            )
        ).toResponse()
    }

    fun getGameReviewList(pageable: Pageable): Page<GameReviewResponse> {
        return gameReviewRepository.findAll(pageable).map { it.toResponse() }
    }
}