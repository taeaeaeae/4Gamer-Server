package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZoneId
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        private set


    @PreUpdate
    fun preUpdate() {

        updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}