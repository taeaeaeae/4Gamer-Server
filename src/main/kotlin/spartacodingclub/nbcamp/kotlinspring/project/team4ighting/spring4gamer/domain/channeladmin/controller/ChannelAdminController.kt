package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackList
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.service.ChannelAdminService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal
import java.util.*

@RestController
@RequestMapping("/api/v1/channel-admin")
class ChannelAdminController(
    private val channelAdminService: ChannelAdminService
) {
    @PostMapping("/channels/{channelId}/boards")
    fun creatBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestBody request: CreateBoardRequest
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelAdminService.createBoard(channelId, request, principal.id))
    } // 채널 게시판 생성

    @PutMapping("/channels/{channelId}/boards/{boardId}")
    fun updateBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @RequestBody request: UpdateBoardRequest
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateBoard(channelId, boardId, request, principal.id))
    } // 채널 게시판 수정

    @DeleteMapping("/channels/{channelId}/boards/{boardId}")
    fun deleteBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
    ): ResponseEntity<Unit> {
        channelAdminService.deleteBoard(channelId, boardId, principal.id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 채널 게시판 삭제

    @PostMapping("/channels/{channelId}/blacklist")
    fun doBlackMember(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: UUID
    ): ResponseEntity<ChannelBlackList> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelAdminService.doBlack(channelId, memberId, principal.id))
    } // 회원 이용 차단

    @DeleteMapping("/channels/{channelId}/blacklist")
    fun unBlackMember(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: UUID
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(channelAdminService.unBlack(channelId, memberId, principal.id))
    } // 회원 이용 차단 해제

    @PutMapping("/{channelId}")
    fun updateChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestBody request: UpdateChannelRequest
    ): ResponseEntity<ChannelResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateChannel(channelId, request, principal.id))
    } // 채널 수정

    @DeleteMapping("/{channelId}")
    fun deleteChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
    ): ResponseEntity<Unit> {
        channelAdminService.deleteChannel(channelId, principal.id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 채널 삭제
}