package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.PostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.toPostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional
    fun createPost(channelId: Long, boardId: Long, request: CreatePostRequest, memberId: Long): PostResponse {
        // TODO: 각 Entity 구현 후 주석 해제
        // val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFountException("Channel", channelId)
        // val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
//        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        return postRepository.save(
            Post.from(
                request = CreatePostRequest(
                    title = request.title,
                    body = request.body
                ),
                board = Board(), // TODO: Board 구현 후 Board() -> 위 board로 대체
                memberId = memberId,
                auther = "TEST USER" // TODO: Member 구현 후 삭제
//                auther = member.nickname // TODO: Member 구현 후 사용
            )
        ).toResponse()
    }

    fun getPostList(channelId: Long, boardId: Long, pageable: Pageable): Page<PostSimplifiedResponse> {
        // TODO: 각 Entity 구현 후 주석 해제
        // val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFountException("Channel", channelId)
        // val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("Board", boardId)

        return postRepository
            .findByBoard(boardId, pageable)
            .map { it.toPostSimplifiedResponse() }
    }

    fun getPost(
        channelId: Long, boardId: Long, postId: Long
    ): PostResponse {

        // TODO: 각 Entity 구현 후 주석 해제
        // val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFountException("Channel", channelId)
        // val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId)

        return post.toResponse()
    }

    @Transactional
    fun updatePost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: UpdatePostRequest,
        memberId: Long
    ): PostResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if (post.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게시글에 대한 수정 권한이 없습니다.")
        }

        post.update(
            title = request.title,
            body = request.body
        )

        return post.toResponse()
    }

    @Transactional
    fun deletePost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        memberId: Long
    ) {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if (post.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게시글에 대한 삭제 권한이 없습니다.")
        }

        postRepository.delete(post)
    }
}

// TODO: Board 구현 후 삭제
class Board {
    val id = 1L
    val title = "Test Board Tile"
    val introduction = "Test Introduction"
    val channel: Channel = Channel()
    val createdAt = ZonedDateTime.of(2024, 7, 16, 12, 12, 12, 12, ZoneId.of("Asia/Seoul"))
    val updatedAt = ZonedDateTime.of(2024, 7, 16, 12, 12, 12, 12, ZoneId.of("Asia/Seoul"))

    class Channel {
        val id = 1L
        val title = "Test Channel Title"
        val gameTitle = "Test Channel Game"
        val introduction = "Test Channel Introduction"
        val alias = "Test Channel Alias"

        val createdAt = ZonedDateTime.of(2024, 7, 16, 12, 12, 12, 12, ZoneId.of("Asia/Seoul"))
        val updatedAt = ZonedDateTime.of(2024, 7, 16, 12, 12, 12, 12, ZoneId.of("Asia/Seoul"))
    }
}