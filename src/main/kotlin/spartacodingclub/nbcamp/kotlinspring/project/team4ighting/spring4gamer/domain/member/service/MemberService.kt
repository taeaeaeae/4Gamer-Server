package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberBlacklistRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.UUID

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberBlacklistRepository: MemberBlacklistRepository
) {

    fun getMember(id: UUID): MemberResponse =

        memberRepository.findByIdOrNull(id)
            ?.toResponse()
            ?: throw EntityNotFoundException("model not found")


    fun getSimpleMember(id: UUID): MemberSimplifiedResponse =

        memberRepository.findByIdOrNull(id)
            ?.toSimplifiedResponse()
            ?: throw EntityNotFoundException("model not found")


    fun addBlacklist(
        memberId: UUID,
        targetId: UUID
    ) {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)
        val target = memberRepository.findByIdOrNull(targetId)
            ?: throw ModelNotFoundException("Member", targetId)

        if (memberBlacklistRepository.existsByIdSubjectIdAndIdTargetId(memberId, targetId))
            throw IllegalArgumentException("Blacklist already added")

        memberBlacklistRepository.save(
            MemberBlacklist.from(
                subject = member,
                target = target
            )
        )
    }


    fun removeBlacklist(
        memberId: UUID,
        targetId: UUID
    ) {

        val targetMemberBlacklist = memberBlacklistRepository.findByIdSubjectIdAndIdTargetId(memberId, targetId)
            ?: throw ModelNotFoundException("MemberBlacklist", "${memberId}/${targetId}")

        memberBlacklistRepository.delete(targetMemberBlacklist)
    }
}