package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MessageResponse

@Entity
@Table(name = "message")
class Message private constructor(
    subject: Member,
    target: Member,
    message: String
) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Member? = subject

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    val target: Member? = target

    @Column(name = "message")
    val message: String = message


    companion object {

        fun from(
            subject: Member,
            target: Member,
            message: String
        ): Message =

            Message(
                subject = subject,
                target = target,
                message = message
            )
    }
}


fun Message.toResponse(): MessageResponse =

    MessageResponse(
        subjectId = subject!!.id,
        targetId = target!!.id,
        message = message
    )