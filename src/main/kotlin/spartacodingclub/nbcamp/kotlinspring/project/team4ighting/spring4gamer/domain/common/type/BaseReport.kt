package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@MappedSuperclass
open class BaseReport (

    @Column(name = "reason", nullable = false)
    var reason: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var subject: Member? = null
) : BaseTimeEntity() {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    var status: ReportStatus = ReportStatus.SUBMITTED
}