package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackList
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.service.ChannelAdminService
import java.util.*

@RestController
@RequestMapping("/api/v1/channel-admin")
class ChannelAdminController(
    private val channelAdminService: ChannelAdminService
) {
    @PostMapping("/channels/{channelId}/boards")
    fun creatBoard(
        // @AuthenticationPrincipal
        @PathVariable channelId: Long,
        @RequestBody request: CreateBoardRequest
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelAdminService.createBoard(channelId, request))
    } // 채널 게시판 생성

    @PutMapping("/channels/{channelId}/boards/{boardId}")
    fun updateBoard(
        // @AuthenticationPrincipal
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @RequestBody request: UpdateBoardRequest
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateBoard(channelId, boardId, request))
    } // 채널 게시판 수정

    @DeleteMapping("/channels/{channelId}/boards/{boardId}")
    fun deleteBoard(
        // @AuthenticationPrincipal
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
    ): ResponseEntity<Unit> {
        channelAdminService.deleteBoard(channelId, boardId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 채널 게시판 삭제

    @PostMapping("/channels/{channelId}/blacklist")
    fun doBlackMember(
        // @AuthenticationPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: UUID
    ): ResponseEntity<ChannelBlackList> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelAdminService.doBlack(channelId, memberId))
    } // 회원 이용 차단

    @DeleteMapping("/channels/{channelId}/blacklist")
    fun unBlackMember(
        // @AuthenticationPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: UUID
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(channelAdminService.unBlack(channelId, memberId))
    } // 회원 이용 차단 해제

    @PutMapping("/{channelId}")
    fun updateChannel(
        // @AuthenticationPrincipal
        @PathVariable channelId: Long,
        @RequestBody request: UpdateChannelRequest
    ): ResponseEntity<ChannelResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateChannel(channelId, request))
    } // 채널 수정

    @DeleteMapping("/{channelId}")
    fun deleteChannel(
        // @AuthenticationPrincipal
        @PathVariable channelId: Long,
    ): ResponseEntity<Unit> {
        channelAdminService.deleteChannel(channelId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    } // 채널 삭제
}