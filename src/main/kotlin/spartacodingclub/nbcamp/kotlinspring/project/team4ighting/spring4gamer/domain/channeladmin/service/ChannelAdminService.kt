package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackList
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackListId
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository.ChannelAdminRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository.ChannelBlackListRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class ChannelAdminService(
    private val channelAdminRepository: ChannelAdminRepository,
    private val channelRepository: ChannelRepository,
    private val channelBlackListRepository: ChannelBlackListRepository,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun createBoard(channelId: Long, request: CreateBoardRequest, id: UUID): BoardResponse {
        val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFoundException("Channel", channelId)
        if (request.title.length !in 1..64) {
            throw IllegalStateException("제목은 최소 1자에서 64자까지 가능")
        } else if (request.introduction.length !in 10..256) {
            throw IllegalStateException("소개글은 최소 10자에서 256자까지 가능")
        }
        return channelAdminRepository.save(
            Board(
                title = request.title,
                alias = channel.alias,
                gameTitle = channel.gameTitle,
                introduction = channel.introduction,
                channel = channel,
                post = listOf(),
            )
        ).toResponse()
    }

    @Transactional
    fun updateBoard(channelId: Long, boardId: Long, request: UpdateBoardRequest, id: UUID): BoardResponse {
        val board = channelAdminRepository.findByChannelIdAndId(channelId, boardId)
            ?: throw ModelNotFoundException("Channel", channelId)
        board.update(request)
        return board.toResponse()
    }

    @Transactional
    fun deleteBoard(channelId: Long, boardId: Long, id: UUID) {
        val board = channelAdminRepository.findByChannelIdAndId(channelId, boardId)
            ?: throw ModelNotFoundException("Channel", channelId)
        channelAdminRepository.delete(board)
    }

    @Transactional
    fun doBlack(channelId: Long, memberId: UUID, id: UUID): ChannelBlackList {
        val channel = channelRepository.findChannelById(channelId)
        val member = memberRepository.findByIdOrNull(memberId)
        return channelBlackListRepository.save(
            ChannelBlackList.doBlack(
                channel,
                member!!,
            )
        )
    }

    @Transactional
    fun unBlack(channelId: Long, memberId: UUID, id: UUID) {
        val channelBlackListId = ChannelBlackListId()
        channelBlackListId.channel = channelRepository.findChannelById(channelId)
        channelBlackListId.member = memberRepository.findByIdOrNull(memberId)
        val black = channelBlackListRepository.findByChannelBlacklistId(channelBlackListId)
        black?.let { channelBlackListRepository.delete(it) }
    }

    @Transactional
    fun updateChannel(channelId: Long, request: UpdateChannelRequest, id: UUID): ChannelResponse {
        val channel = channelRepository.findChannelById(channelId)
        channel.update(request)
        return channel.toResponse()
    }


    @Transactional
    fun deleteChannel(channelId: Long, id: UUID) {
        val channel = channelRepository.findChannelById(channelId)
        channelRepository.delete(channel)
    }
}