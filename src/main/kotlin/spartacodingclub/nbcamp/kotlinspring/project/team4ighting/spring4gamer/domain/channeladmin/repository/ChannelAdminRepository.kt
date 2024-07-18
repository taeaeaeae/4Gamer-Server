package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board

interface ChannelAdminRepository : JpaRepository<Board, Long> {
    fun findByIdAndChannelId(boardId: Long, channelId: Long): Board?
}