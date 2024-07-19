package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.dto.response.BoardResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.service.BoardService

@RestController
@RequestMapping("/api/v1/channels/{channelId}")
class BoardController(
    private val boardService: BoardService,
) {

    @GetMapping("/boards")
    fun getBoardList(
        @PathVariable channelId: Long,
    ): ResponseEntity<List<BoardResponse>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(boardService.getBoardList(channelId))

}