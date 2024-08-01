package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.CreateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.request.UpdateGameReviewRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewReportResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.service.GameReviewService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1/game-reviews")
class GameReviewController(
    private val gameReviewService: GameReviewService
) {

    @PostMapping
    fun createGameReview(
        @AuthenticationPrincipal member: MemberPrincipal,
        @RequestBody @Valid request: CreateGameReviewRequest
    ): ResponseEntity<GameReviewResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(gameReviewService.createGameReview(request, member.id))


    @GetMapping
    fun getGameReviewList(
        @PageableDefault(
            page = 0, size = 10,
            sort = ["createdAt"], direction = Sort.Direction.DESC
        ) pageable: Pageable
    ): ResponseEntity<Page<GameReviewResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(gameReviewService.getGameReviewList(pageable))


    @GetMapping("/{gameReviewId}")
    fun getGameReview(
        @PathVariable gameReviewId: Long
    ): ResponseEntity<GameReviewResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(gameReviewService.getGameReview(gameReviewId))


    @PutMapping("/{gameReviewId}")
    fun updateGameReview(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable gameReviewId: Long,
        @RequestBody @Valid request: UpdateGameReviewRequest
    ): ResponseEntity<GameReviewResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(gameReviewService.updateGameReview(gameReviewId, request, member.id))


    @DeleteMapping("/{gameReviewId}")
    fun deleteGameReview(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable gameReviewId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(gameReviewService.deleteGameReview(gameReviewId, member.id))


    /*
     * 반응 관련
     */

    @PutMapping("/{gameReviewId}/reaction")
    fun addReaction(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable gameReviewId: Long,
        @RequestParam("is-upvoting") isUpvoting: Boolean
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(gameReviewService.addReaction(gameReviewId, member.id, isUpvoting))


    @DeleteMapping("/{gameReviewId}/reaction")
    fun deleteReaction(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable gameReviewId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(gameReviewService.deleteReaction(gameReviewId, member.id))


    @PostMapping("/{gameReviewId}/report")
    fun reportGameReview(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable gameReviewId: Long,
        @RequestBody reason: String
    ): ResponseEntity<GameReviewReportResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(gameReviewService.reportGameReview(gameReviewId, member.id, reason))


    @GetMapping("/top-reviews")
    fun getTopReviews(): ResponseEntity<List<GameReviewResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(gameReviewService.getTopReviews())
}