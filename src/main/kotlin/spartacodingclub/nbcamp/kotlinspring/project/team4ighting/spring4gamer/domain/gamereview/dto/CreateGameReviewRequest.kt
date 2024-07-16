package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto

data class CreateGameReviewRequest(
    val gameTitle: String,
    val point: Byte,
    val description: String
)