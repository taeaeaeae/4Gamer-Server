package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.repository.BoardRepository

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {

    @Transactional
    fun getBoardList(channelId: Long): List<BoardResponse> =

        boardRepository.findAll()
            .map { it.toResponse() }
}
