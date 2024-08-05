package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.CreateBoardRequest
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request.UpdateBoardRequest

@Entity
@Table(name = "board")
class Board private constructor(
    title: String,
    introduction: String,
    channel: Channel
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title", nullable = false)
    var title: String = title
        private set

    @Column(name = "introduction", nullable = false)
    var introduction: String = introduction
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    val channel: Channel = channel


    companion object {

        fun from(
            request: CreateBoardRequest,
            channel: Channel
        ): Board =

            Board(
                title = request.title,
                introduction = request.introduction,
                channel = channel
            )
    }


    fun update(updateBoardRequest: UpdateBoardRequest) {

        title = updateBoardRequest.title
        introduction = updateBoardRequest.introduction
        preUpdate()
    }
}

fun Board.toResponse(): BoardResponse =

    BoardResponse(
        id = id!!,
        title = title,
        createdAt = createdAt,
        updatedAt = updatedAt
    )