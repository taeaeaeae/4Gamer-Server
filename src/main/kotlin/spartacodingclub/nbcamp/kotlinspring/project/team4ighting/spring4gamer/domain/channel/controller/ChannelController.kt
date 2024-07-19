package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.request.CreateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.service.ChannelService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1/channels")
class ChannelController(
    private val channelService: ChannelService,
) {

    // 채널 생성
    @PostMapping
    fun creatChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody @Valid request: CreateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelService.createChannel(request, principal.id))


    // 채널 목록 조회
    @GetMapping
    fun getChannelList(@PageableDefault(size = 100) pageable: Pageable): ResponseEntity<Slice<ChannelResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getChannelList(pageable))


    // 채널 단건 조회
    @GetMapping("/{channelId}")
    fun getChannel(@PathVariable channelId: Long): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelService.getChannel(channelId))
}