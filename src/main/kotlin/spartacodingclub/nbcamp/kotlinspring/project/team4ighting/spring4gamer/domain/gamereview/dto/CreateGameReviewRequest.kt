package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class CreateGameReviewRequest(
    val gameTitle: String,
    @field:Min(value = 0, message = "최소 점수는 0점입니다.")
    @field:Max(value = 10, message = "최대 점수는 10점입니다.")
    val point: Byte,
    @field:Size(min = 10, max = 1024, message = "최소 10자에서 최대 1024자까지 입력 가능합니다.")
    val description: String
)