package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.service.ChannelService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1/channels")
class ChannelController(
    private val channelService: ChannelService,
) {

    @PostMapping
    fun creatChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody request: CreateChannelRequest
    ): ResponseEntity<ChannelResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelService.createChannel(request, principal.id))
    } // 채널 생성

    @GetMapping
    fun getChannelList(): ResponseEntity<Slice<ChannelResponse>> {
        val pageable = Pageable.ofSize(100)
        return ResponseEntity.status(HttpStatus.OK).body(channelService.getChannelList(pageable))
    } // 채널 목록 조회

    @GetMapping("/{channelId}")
    fun getChannel(
        @PathVariable("channelId") channelId: Long,
    ): ResponseEntity<ChannelResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.getChannel(channelId))
    }// 채널 단건 조회
}