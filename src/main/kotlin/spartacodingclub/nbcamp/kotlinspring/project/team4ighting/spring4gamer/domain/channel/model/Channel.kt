package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateChannelRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "channel")
class Channel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "gameTitle", nullable = false)
    var gameTitle: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "alias", nullable = false)
    var alias: String,

    @Column(name = "admin", nullable = false)
    var admin: UUID,

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, orphanRemoval = true)
    val board: List<Board>
) {
    fun update(updateChannelRequest: UpdateChannelRequest) {
        title = updateChannelRequest.title
        introduction = updateChannelRequest.introduction
        updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    var createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
}

fun Channel.toResponse(): ChannelResponse {
    return ChannelResponse(
        id!!, title, gameTitle, introduction, alias,
    )
}