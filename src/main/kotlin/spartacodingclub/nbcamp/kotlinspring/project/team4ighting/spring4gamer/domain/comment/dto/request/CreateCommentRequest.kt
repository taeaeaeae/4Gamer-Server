package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.dto.request

import jakarta.validation.constraints.Size

data class CreateCommentRequest(
    @field:Size(min = 2, max = 256, message = "최소 2자에서 최대 256자까지 입력 가능합니다.")
    val content: String
)