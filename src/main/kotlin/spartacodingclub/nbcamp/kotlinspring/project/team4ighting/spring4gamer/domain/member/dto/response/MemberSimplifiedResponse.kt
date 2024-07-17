package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member
import java.util.UUID

class MemberSimplifiedResponse(
    val id: UUID,
    val nickname: String
) {

    companion object {
        fun from(member: Member): MemberSimplifiedResponse =
            MemberSimplifiedResponse(member.id, member.nickname)
    }
}