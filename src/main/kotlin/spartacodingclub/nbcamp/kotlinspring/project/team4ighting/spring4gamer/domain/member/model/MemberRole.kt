package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model

import java.io.InvalidObjectException

enum class MemberRole {
    USER,
    ADMIN,
    CHANNEL_ADMIN;

    companion object {
        fun from(role: String): MemberRole {
            return try {
                valueOf(role.uppercase())
            } catch (e: IllegalArgumentException) {
                throw InvalidObjectException("정의되지 않은 롤")
            }
        }
    }
}