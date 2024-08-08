package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request

import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field: Size(min = 2, max = 16, message = "최소 2자에서 최대 16자까지 입력 가능합니다.")
    val nickname: String
)