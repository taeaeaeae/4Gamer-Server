package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.service.CommentService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1/channels/{channelId}/boards/{boardId}/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        @RequestBody @Valid request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(channelId, boardId, postId, request, member.id))

    @GetMapping
    fun getCommentList(
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<CommentResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList(channelId, boardId, postId, pageable))


    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody @Valid request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(channelId, boardId, postId, commentId, request, member.id))


    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(channelId, boardId, postId, commentId, member.id))
}