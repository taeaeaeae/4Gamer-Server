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
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.request.CreatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.request.UpdatePostRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostReactionRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.CookieUtil
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val postReactionRepository: PostReactionRepository
) {

    @Transactional
    fun createPost(
        channelId: Long,
        boardId: Long,
        request: CreatePostRequest,
        memberId: UUID
    ): PostResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

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
    ): Page<PostSimplifiedResponse> =

        postRepository.findByBoard(
            board = (boardRepository.findByIdAndChannelId(boardId, channelId)
                ?: throw ModelNotFoundException("Board", boardId)),
            pageable = pageable
        ).map { it.toPostSimplifiedResponse() }


    @Transactional
    fun getPost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): PostResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)

        viewCountUp(post, request, response)

        return postRepository.save(post).toResponse()
    }


    @Transactional
    fun updatePost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: UpdatePostRequest,
        memberId: UUID
    ): PostResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val targetPost = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)

        if (targetPost.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게시글에 대한 수정 권한이 없습니다.")
        }

        targetPost.update(
            title = request.title,
            body = request.body
        )

        return postRepository.save(targetPost).toResponse()
    }


    @Transactional
    fun deletePost(
        channelId: Long,
        boardId: Long,
        postId: Long,
        memberId: UUID
    ) {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val targetPost = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)

        if (targetPost.memberId != memberId) {
            throw CustomAccessDeniedException("해당 게시글에 대한 삭제 권한이 없습니다.")
        }

        val comments = commentRepository.findAllByPostId(targetPost.id!!)

        commentRepository.deleteAllInBatch(comments)
        postRepository.delete(targetPost)
    }


    @Transactional
    fun addReaction(
        channelId: Long,
        boardId: Long,
        postId: Long,
        memberId: UUID,
        isUpvoting: Boolean
    ) {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val targetPost = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

        val reaction = postReactionRepository.findByIdPostIdAndIdMemberId(postId, memberId)

        if (reaction == null) {
            val newReaction = PostReaction.from(member, targetPost, isUpvoting)

            targetPost.increaseReaction(isUpvoting)
            postReactionRepository.save(newReaction)
        } else {
            targetPost.applySwitchedReaction(isUpvoting)
            reaction.isUpvoting = isUpvoting
        }
    }


    @Transactional
    fun deleteReaction(
        channelId: Long,
        boardId: Long,
        postId: Long,
        memberId: UUID
    ) {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val targetPost = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)
        val reaction = postReactionRepository.findByIdPostIdAndIdMemberId(postId, memberId)
            ?: throw ModelNotFoundException("PostReaction", "${postId}/${memberId}")

        targetPost.decreaseReaction(reaction.isUpvoting)
        postReactionRepository.delete(reaction)
    }

    private fun viewCountUp(
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