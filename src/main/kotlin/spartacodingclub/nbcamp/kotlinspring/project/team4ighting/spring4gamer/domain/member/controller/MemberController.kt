package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service.MemberService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/member")
    fun getMember(@AuthenticationPrincipal principal: MemberPrincipal): ResponseEntity<MemberResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getMember(principal.id))


    @GetMapping("/members/{id}")
    fun getSimpleMember(@PathVariable id: String): ResponseEntity<MemberSimplifiedResponse> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getSimpleMember(UUID.fromString(id)))


    @PostMapping("/member/blacklist")
    fun addBlacklist(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestParam("target-id") targetId: UUID
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.addBlacklist(principal.id, targetId))


    @DeleteMapping("/member/blacklist")
    fun removeBlacklist(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestParam("target-id") targetId: UUID
    ): ResponseEntity<Unit> =

        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(memberService.removeBlacklist(principal.id, targetId))
}