package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository.BoardRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlacklistId
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository.ChannelBlacklistRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class ChannelAdminService(
    private val channelRepository: ChannelRepository,
    private val channelBlacklistRepository: ChannelBlacklistRepository,
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
    private val boardRepository: BoardRepository,
    private val postRepository: PostRepository,
) {

    @Transactional
    fun createBoard(
        channelId: Long,
        request: CreateBoardRequest,
    ): BoardResponse {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel", channelId)

        return boardRepository.save(
            Board.from(
                request = request,
                channel = channel
            )
        ).toResponse()
    }


    @Transactional
    fun updateBoard(
        channelId: Long,
        boardId: Long,
        request: UpdateBoardRequest,
    ): BoardResponse {

        val targetBoard = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Channel", channelId)
        targetBoard.update(request)
        return boardRepository.save(targetBoard).toResponse()
    }


    @Transactional
    fun deleteBoard(
        channelId: Long,
        boardId: Long,
    ) {

        val targetBoard = boardRepository.findByIdAndChannelId(boardId, channelId)
            ?: throw ModelNotFoundException("Channel", channelId)
        val subPosts = postRepository.findAllByBoardId(targetBoard.id!!)
        val subComments = commentRepository.findAllByPostIdIn(subPosts.map { it.id!! })

        commentRepository.deleteAllInBatch(subComments)
        postRepository.deleteAllInBatch(subPosts)
        boardRepository.delete(targetBoard)
    }


    @Transactional
    fun doBlack(
        channelId: Long,
        memberId: UUID,
    ): ChannelBlacklist {

        val channel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel", channelId)
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)

        return channelBlacklistRepository.save(
            ChannelBlacklist.from(
                channel,
                member,
            )
        )
    }


    @Transactional
    fun unBlack(
        channelId: Long,
        memberId: UUID,
    ) {

        val channelBlacklistId = ChannelBlacklistId(
            channel = (channelRepository.findByIdOrNull(channelId)
                ?: throw ModelNotFoundException("Channel", channelId)),
            member = (memberRepository.findByIdOrNull(memberId)
                ?: throw ModelNotFoundException("Member", memberId))
        )
        val targetBlacklist = channelBlacklistRepository.findByIdOrNull(channelBlacklistId)
            ?: throw ModelNotFoundException("ChannelBlacklist", channelBlacklistId)

        channelBlacklistRepository.delete(targetBlacklist)
    }


    @Transactional
    fun updateChannel(
        channelId: Long,
        request: UpdateChannelRequest,
    ): ChannelResponse {

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel", channelId)
        targetChannel.update(request)
        return channelRepository.save(targetChannel).toResponse()
    }


    @Transactional
    fun deleteChannel(channelId: Long) {

        val targetChannel = channelRepository.findByIdOrNull(channelId)
            ?: throw ModelNotFoundException("Channel", channelId)

        val subBoards = boardRepository.findAllByChannelId(channelId)
        val subPosts = postRepository.findAllByBoardIdIn(subBoards.map { it.id!! })
        val subComments = commentRepository.findAllByPostIdIn(subPosts.map { it.id!! })

        commentRepository.deleteAllInBatch(subComments)
        postRepository.deleteAllInBatch(subPosts)
        boardRepository.deleteAllInBatch(subBoards)
        channelRepository.delete(targetChannel)
    }
}