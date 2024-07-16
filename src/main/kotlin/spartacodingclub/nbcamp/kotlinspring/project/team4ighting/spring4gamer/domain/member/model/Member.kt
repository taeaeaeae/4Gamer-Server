package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import jakarta.persistence.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "member")
class Member(
    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "level", nullable = false)
    val level: Int = 0,

    @Column(name = "exp", nullable = false)
    val exp: Int = 0,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime? = null,

    @Column(name = "password_updated_at")
    val passwordUpdatedAt: ZonedDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null

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