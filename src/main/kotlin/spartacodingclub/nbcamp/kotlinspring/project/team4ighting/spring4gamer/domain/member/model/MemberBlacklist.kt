package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "member_blacklist")
class MemberBlacklist private constructor (
    subject: Member,
    target: Member
) {

    @EmbeddedId
    val id: MemberBlacklistId = MemberBlacklistId(
        subject = subject,
        target = target
    )


    companion object {

        fun from(subject: Member, target: Member): MemberBlacklist =

            MemberBlacklist(
                subject = subject,
                target = target
            )
    }
}