package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.request

import jakarta.validation.constraints.Size

data class UpdateMemberPasswordRequest (
    @field: Size(min = 8, max = 64, message = "최소 8자에서 최대 64자까지 입력 가능합니다.")
    val password: String,
    )