package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "member")
class Member(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String,

    @Column(name = "lv", nullable = false)
    var lv: Int = 0,

    @Column(name = "exp_point", nullable = false)
    var expPoint: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: MemberRole = MemberRole.USER,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null,

    @Column(name = "password_updated_at")
    var passwordUpdatedAt: ZonedDateTime? = null,
) {

    companion object {
        fun from(email: String, password: String, nickname: String): Member {
            return Member(
                email = email,
                password = password,
                nickname = nickname,
                createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            )
        }
    }

}