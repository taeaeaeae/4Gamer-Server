package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import java.util.UUID

@Service
class MemberService(
    private val repository: MemberRepository
) {

    fun getMember(id: UUID): MemberResponse {

        val member = repository.findByIdOrNull(id) ?: throw EntityNotFoundException("model not found")
        return MemberResponse.from(member)
    }

    fun getSimpleMember(id: UUID): MemberSimplifiedResponse {
        
        val member = repository.findByIdOrNull(id) ?: throw EntityNotFoundException("model not found")
        return MemberSimplifiedResponse.from(member)
    }
}