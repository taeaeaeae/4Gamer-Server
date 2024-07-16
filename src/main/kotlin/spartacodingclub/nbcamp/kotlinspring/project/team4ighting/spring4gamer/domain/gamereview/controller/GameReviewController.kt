package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.UpdateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service.GameReviewService

@RestController
@RequestMapping("/api/v1/reviews")
class GameReviewController(
    private val gameReviewService: GameReviewService
) {

    @PostMapping
    fun createGameReview(
//        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @RequestBody request: CreateGameReviewRequest
    ): ResponseEntity<GameReviewResponse> {
        // TODO: 로그인 구현 후 임시 유저 ID인 1L 제거
        return ResponseEntity.status(HttpStatus.CREATED).body(
            gameReviewService.createGameReview(request, 1L)
        )
    }

    @GetMapping
    fun getGameReviewList(
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<GameReviewResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(gameReviewService.getGameReviewList(pageable))
    }

    @GetMapping("/{reviewId}")
    fun getGameReview(@PathVariable reviewId: Long): ResponseEntity<GameReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(gameReviewService.getGameReview(reviewId))
    }

    @PutMapping("/{reviewId}")
    fun updateGameReview(
//        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @PathVariable reviewId: Long, @RequestBody request: UpdateGameReviewRequest
    ): ResponseEntity<GameReviewResponse> {
        // TODO: 로그인 구현 후 임시 유저 ID인 1L 제거
        return ResponseEntity.status(HttpStatus.OK).body(gameReviewService.updateGameReview(reviewId, request, 1L))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteGameReview(
//        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @PathVariable reviewId: Long
    ): ResponseEntity<GameReviewResponse> {
        gameReviewService.deleteGameReview(reviewId, 1L)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
    
    // TODO: 리뷰 신고 - POST
}