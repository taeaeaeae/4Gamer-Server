package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository.BoardRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.response.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.request.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.request.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.CustomAccessDeniedException
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val boardRepository: BoardRepository
) {

    @Transactional
    fun createComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: CreateCommentRequest,
        memberId: UUID
    ): CommentResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

        return commentRepository.save(
            Comment.from(
                content = request.content,
                memberId = memberId,
                post = post,
                author = member.nickname
            )
        ).toResponse()
    }


    fun getCommentList(
        channelId: Long,
        boardId: Long,
        postId: Long,
        pageable: Pageable
    ): Page<CommentResponse> {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)

        return commentRepository.findByPost(post, pageable)
            .map { it.toResponse() }
    }


    @Transactional
    fun updateComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest,
        memberId: UUID
    ): CommentResponse {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)
        val targetComment = commentRepository.findByIdAndPost(commentId, post)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (targetComment.memberId != memberId) {
            throw CustomAccessDeniedException("해당 댓글에 대한 수정 권한이 없습니다.")
        }

        targetComment.update(
            content = request.content
        )

        return commentRepository.save(targetComment).toResponse()
    }


    @Transactional
    fun deleteComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        commentId: Long,
        memberId: UUID
    ) {

        val board = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, board)
            ?: throw ModelNotFoundException("Post", postId)
        val targetComment = commentRepository.findByIdAndPost(commentId, post)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (targetComment.memberId != memberId) {
            throw CustomAccessDeniedException("해당 댓글에 대한 삭제 권한이 없습니다.")
        }

        commentRepository.delete(targetComment)
    }
}