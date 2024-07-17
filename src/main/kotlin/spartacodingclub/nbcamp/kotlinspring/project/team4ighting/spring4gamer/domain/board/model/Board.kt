package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.UpdateChannelRequest
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name = "board")
class Board(

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    val channel: Channel,

    @OneToMany(mappedBy= "board", fetch = FetchType.LAZY, orphanRemoval = true)
    val post: Post
) {
    fun update(updateBoardRequest: UpdateBoardRequest) {
        title = updateBoardRequest.title
        introduction = updateBoardRequest.introduction
        updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    var createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
}

fun Board.toResponse(): BoardResponse {
    return BoardResponse(
        id!!, title
    )
}
