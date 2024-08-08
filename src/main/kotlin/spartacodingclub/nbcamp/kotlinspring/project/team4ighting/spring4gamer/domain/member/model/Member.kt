package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request.UpdateProfileRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "member")
class Member private constructor(
    email: String,
    password: String,
    nickname: String
) : BaseTimeEntity() {

    @Id
    val id: UUID = UUID.randomUUID()

    @Column(name = "email", nullable = false, unique = true)
    val email: String = email

    @Column(name = "password", nullable = false)
    var password: String = password

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String = nickname

    @Column(name = "lv", nullable = false)
    var lv: Int = 0
        private set

    @Column(name = "exp_point", nullable = false)
    var expPoint: Int = 0
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: MemberRole = MemberRole.USER
        private set

    @Column(name = "password_updated_at")
    var passwordUpdatedAt: ZonedDateTime? = null
        private set


    companion object {

        fun from(email: String, password: String, nickname: String): Member =

            Member(
                email = email,
                password = password,
                nickname = nickname,
            )
    }

    fun update(nickname: String) {
        this.nickname = nickname
        preUpdate()
    }


    fun assignChannelAdmin() {

        role = MemberRole.CHANNEL_ADMIN
        preUpdate()
    }
}

fun Member.toResponse(): MemberResponse =

    MemberResponse(
        id = id,
        email = email,
        nickname = nickname
    )


fun Member.toSimplifiedResponse(): MemberSimplifiedResponse =

    MemberSimplifiedResponse(
        id = id,
        nickname = nickname
    )