package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service.PostService

@RestController
@RequestMapping("/api/v1/channels/{channelId}/boards/{boardId}/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(
//        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @PathVariable("channelId") channelId: Long,
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        // TODO: 로그인 구현 후 임시 유저 ID인 1L 제거
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(channelId, boardId, request, 1L))
    }
    // TODO: 게시글 목록 조회 - GET
    // TODO: 게시글 상세 조회 - GET
    // TODO: 게시글 수정 - PUT
    // TODO: 게시글 삭제 - DELETE
}