package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb.service.IgdbService

@RestController
@RequestMapping("/api/v1/igdb")
class IgdbController(
    private val igdbService: IgdbService,
) {

    @Scheduled(fixedRate = 600000)
    fun refreshAccessToken() {
        igdbService.getAccessToken()
    }


    // token 생성
    @PostMapping("/get-token")
    fun getAccessToken(): ResponseEntity<String> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(igdbService.getAccessToken())


    // 단일 게임 정보 반환
    @PostMapping("/get-info")
    fun getGamesById(
        @RequestParam gameId: String,
    ): ResponseEntity<ResponseEntity<String>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(igdbService.getGamesById(gameId))


    // 게임 이름 검색
    @PostMapping("/get-name")
    fun searchGamesByName(
        @RequestParam gameTitle: String,
    ): ResponseEntity<ResponseEntity<String>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(igdbService.searchGamesByName(gameTitle))


    // 게임 네임 사용 가능 체크
    @PostMapping("/check-name")
    fun checkGamesName(
        @RequestParam gameTitle: String,
    ): ResponseEntity<Boolean> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(igdbService.checkGamesName(gameTitle))


    // Top 10 게임
    @PostMapping("/top-games")
    fun getTopGames(): ResponseEntity<ResponseEntity<String>> =

        ResponseEntity
            .status(HttpStatus.OK)
            .body(igdbService.getTopGames())
}