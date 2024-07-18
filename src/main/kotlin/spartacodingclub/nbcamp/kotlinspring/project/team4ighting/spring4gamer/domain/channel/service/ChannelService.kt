package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.service

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
) {
    fun createChannel(request: CreateChannelRequest, id: UUID): ChannelResponse {
        val member = memberRepository.findByIdOrNull(id)
        if (request.title.length !in 1..64) {
            throw IllegalStateException("제목은 최소 1자에서 64자까지 가능")
        } else if (request.gameTitle.length > 129) {
            throw IllegalStateException("게임 제목은 128 이내")
        } else if (request.introduction.length !in 10..256) {
            throw IllegalStateException("소개글은 최소 10자에서 256자까지 가능")
        }

        member!!.role = MemberRole.CHANNEL_ADMIN

        return channelRepository.save(
            Channel(
                title = request.title,
                gameTitle = request.gameTitle,
                introduction = request.introduction,
                alias = request.alias,
                board = listOf(),
                member = member,
                admin = id.toString()
            )
        ).toResponse()
    }

    @Transactional
    fun getChannelList(pageable: Pageable): Slice<ChannelResponse> {
        return channelRepository.findAllBy(pageable)
    }

    @Transactional
    fun getChannel(channelId: Long): ChannelResponse {
        val channel = channelRepository.findByIdOrNull(channelId) ?: throw ModelNotFoundException("Channel", channelId)
        return channel.toResponse()
    }
}