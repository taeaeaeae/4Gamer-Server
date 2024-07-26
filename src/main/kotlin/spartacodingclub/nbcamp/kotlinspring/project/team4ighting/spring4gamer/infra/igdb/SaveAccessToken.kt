package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb

import org.springframework.stereotype.Component

@Component
class SaveAccessToken {

    @Volatile
    var accessToken : String? = null

        @Synchronized set
}