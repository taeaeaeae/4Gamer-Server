package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.response.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.UpdateChannelRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.service.ChannelAdminService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal
import java.util.*

@RestController
@RequestMapping("/api/v1/channel-admin/channels/{channelId}")
class ChannelAdminController(
    private val channelAdminService: ChannelAdminService
) {

    /*
     * 채널 게시판 관련
     */

    // 채널 게시판 생성
    @PostMapping("/boards")
    fun creatBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestBody @Valid request: CreateBoardRequest
    ): ResponseEntity<BoardResponse> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(channelAdminService.createBoard(channelId, request))


    // 채널 게시판 수정
    @PutMapping("/boards/{boardId}")
    fun updateBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
        @RequestBody @Valid request: UpdateBoardRequest
    ): ResponseEntity<BoardResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateBoard(channelId, boardId, request))


    // 채널 게시판 삭제
    @DeleteMapping("/boards/{boardId}")
    fun deleteBoard(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @PathVariable boardId: Long,
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(channelAdminService.deleteBoard(channelId, boardId))


    /*
     * 회원 차단 관련
     */

    // 회원 이용 차단
    @PostMapping("/blacklist")
    fun doBlackMember(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: String
    ): ResponseEntity<ChannelBlacklist> =

        UUID.fromString(memberId).let { memberUUID ->
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(channelAdminService.doBlack(channelId, memberUUID))
        }


    // 회원 이용 차단 해제
    @DeleteMapping("/blacklist")
    fun unBlackMember(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestParam memberId: String
    ): ResponseEntity<Unit> =

        UUID.fromString(memberId).let { memberUUID ->
            ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(channelAdminService.unBlack(channelId, memberUUID))
        }


    /*
     * 채널 자체 관련
     */

    // 채널 수정
    @PutMapping
    fun updateChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
        @RequestBody @Valid request: UpdateChannelRequest
    ): ResponseEntity<ChannelResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(channelAdminService.updateChannel(channelId, request))

    // 채널 삭제
    @DeleteMapping
    fun deleteChannel(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable channelId: Long,
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(channelAdminService.deleteChannel(channelId))
}