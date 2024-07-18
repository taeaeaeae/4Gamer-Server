package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service.PostService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1/channels/{channelId}/boards/{boardId}/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(channelId, boardId, request, member.id))

    @GetMapping
    fun getPostList(
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<PostSimplifiedResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList(channelId, boardId, pageable))

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(channelId, boardId, postId, request, response))

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(channelId, boardId, postId, request, member.id))

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal member: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {

        postService.deletePost(channelId, boardId, postId, member.id)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}