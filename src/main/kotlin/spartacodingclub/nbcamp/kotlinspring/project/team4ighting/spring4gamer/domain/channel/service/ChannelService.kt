package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.service

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository.ChannelRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val memberRepository: MemberRepository,
) {

    fun createChannel(
        request: CreateChannelRequest,
        memberId: UUID
    ): ChannelResponse {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)
        member.assignChannelAdmin()

        memberRepository.save(member)
        return channelRepository.save(
            Channel.from(
                request = request,
                admin = memberId
            )
        ).toResponse()
    }


    @Transactional
    fun getChannelList(pageable: Pageable): Slice<ChannelResponse> =

        channelRepository.findAllBy(pageable)
            .map { it.toResponse() }


    @Transactional
    fun getChannel(channelId: Long): ChannelResponse =

        channelRepository.findByIdOrNull(channelId)
            ?.toResponse()
            ?: throw ModelNotFoundException("Channel", channelId)


    fun getChannelTopPostList(channelId: Long): List<PostResponse> =

        channelRepository.findTopPosts(channelId).map { it.toResponse() }
}