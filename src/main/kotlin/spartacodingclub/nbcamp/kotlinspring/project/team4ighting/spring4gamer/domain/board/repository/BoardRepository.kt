package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.Board

interface BoardRepository : JpaRepository<Board, Long> {

    fun findAllById(id: Long): List<BoardResponse>
}