package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Embeddable
class MemberBlacklistId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    val target: Member? = null
)