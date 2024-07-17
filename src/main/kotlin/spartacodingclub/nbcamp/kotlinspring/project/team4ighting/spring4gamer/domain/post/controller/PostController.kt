package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.UpdatePostRequest
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

    @GetMapping
    fun getPostList(
        @PathVariable("channelId") channelId: Long,
        @PathVariable("boardId") boardId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<PostSimplifiedResponse>> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList(channelId, boardId, pageable))
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable("channelId") channelId: Long,
        @PathVariable("boardId") boardId: Long,
        @PathVariable("postId") postId: Long
    ): ResponseEntity<PostResponse> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(channelId, boardId, postId))
    }

    @PutMapping("/{postId}")
    fun updatePost(
//        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @PathVariable("channelId") channelId: Long,
        @PathVariable("boardId") boardId: Long,
        @PathVariable("postId") postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> {

        // TODO: 로그인 구현 후 임시 유저 ID인 1L 제거
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(channelId, boardId, postId, request, 2L))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        //        @AuthenticationPrincipal memberId: Long, // TODO: 로그인 구현 후 사용 예정
        @PathVariable("channelId") channelId: Long,
        @PathVariable("boardId") boardId: Long,
        @PathVariable("postId") postId: Long
    ): ResponseEntity<Unit> {

        postService.deletePost(channelId, boardId, postId, 1L)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}