package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository.BoardRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
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
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.CookieUtil
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository
) {

    @Transactional
    fun createPost(
        channelId: Long,
        boardId: Long,
        request: CreatePostRequest,
        memberId: UUID
    ): PostResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)
        return postRepository.save(
            Post.from(
                request = CreatePostRequest(
                    title = request.title,
                    body = request.body
                ),
                board = board,
                memberId = memberId,
                author = member.nickname
            )
        ).toResponse()
    }

    fun getPostList(
        channelId: Long,
        boardId: Long,
        pageable: Pageable
    ): Page<PostSimplifiedResponse> {

        // TODO: 각 Entity 구현 후 주석 해제
        // val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFountException("Channel", channelId)
        // val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("Board", boardId)

        return postRepository
            .findByBoardId(boardId, pageable)
            .map { it.toPostSimplifiedResponse() }
    }

    @Transactional
    fun getPost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): PostResponse {

        // TODO: 각 Entity 구현 후 주석 해제
        // val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFountException("Channel", channelId)
        // val board = boardRepository.findByIdOrNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        // TODO: 1. board에서 channelId와 일치하는지 확인
        // TODO: 2. post에서 boardId와 일치하는지 확인
        val post = postRepository.findByIdAndBoardId(postId, boardId) ?: throw ModelNotFoundException("Post", postId)

        viewCountUp(post, request, response)

        return post.toResponse()
    }

    @Transactional
    fun updatePost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: UpdatePostRequest,
        memberId: UUID
    ): PostResponse {

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

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
        memberId: UUID
    ) {

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        if (post.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게시글에 대한 삭제 권한이 없습니다.")
        }

        commentRepository.deleteByPostId(postId)
        postRepository.delete(post)
    }

    fun viewCountUp(
        post: Post,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        request.cookies?.map {
            if (it.name == "viewCounts") {
                if (it.value.contains("[${post.id}]")) {
                    return
                } else {
                    CookieUtil.generateMidnightExpiryCookie(post.id!!, it, response)
                    return post.updateViews()
                }
            }
        }

        CookieUtil.generateMidnightExpiryCookie(post.id!!, null, response)

        return post.updateViews()
    }
}