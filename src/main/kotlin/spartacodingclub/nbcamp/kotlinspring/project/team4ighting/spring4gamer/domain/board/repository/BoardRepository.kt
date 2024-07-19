package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel

interface BoardRepository : JpaRepository<Board, Long> {

    fun findByIdAndChannelId(boardId: Long, channelId: Long): Board?

    fun findAllByChannelId(channelId: Long): List<Board>
}