package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.Comment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {

    @Transactional
    fun createComment(
        channelId: Long,
        boardId: Long,
        postId: Long,
        request: CreateCommentRequest,
        memberId: Long
    ): CommentResponse {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        // TODO: member 구현 후 사용 예정
//        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return commentRepository.save(
            Comment.from(
                content = request.content,
                memberId = memberId,
                post = post,
                author = "TEST USER" // TODO: Member 구현 후 삭제
//                author = member.nickname // TODO: Member 구현 후 사용
            )
        ).toResponse()
    }

    fun getCommentList(
        channelId: Long,
        boardId: Long,
        postId: Long,
        pageable: Pageable
    ): Page<CommentResponse> {

        // TODO: board 구현 후 사용 예정
//        val board = commentRepository.findByIdAndChannel(boardId, channelId) ?: throw ModelNotFoundException("Board", boardId)
        val post = postRepository.findByIdAndBoard(postId, boardId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByPostId(postId, pageable)

        return comment.map { it.toResponse() }
    }
}