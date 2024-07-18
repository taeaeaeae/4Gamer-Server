package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post

interface BoardRepository : JpaRepository<Board, Long> {

    fun findByIdAndChannelId(boardId: Long, channelId: Long): Board?

    fun deleteByChannel(channel: Channel)
}