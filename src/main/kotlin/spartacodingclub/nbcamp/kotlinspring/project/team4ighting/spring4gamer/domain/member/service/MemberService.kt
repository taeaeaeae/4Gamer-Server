package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import java.util.UUID

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun getMember(id: UUID): MemberResponse =

        memberRepository.findByIdOrNull(id)
            ?.toResponse()
            ?: throw EntityNotFoundException("model not found")


    fun getSimpleMember(id: UUID): MemberSimplifiedResponse =

        memberRepository.findByIdOrNull(id)
            ?.toSimplifiedResponse()
            ?: throw EntityNotFoundException("model not found")
}