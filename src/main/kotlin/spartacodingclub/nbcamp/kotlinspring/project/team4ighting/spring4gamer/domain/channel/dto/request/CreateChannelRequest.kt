package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto.request

import jakarta.validation.constraints.Size

data class CreateChannelRequest(
    @field:Size(min = 1, max = 64, message = "제목은 최소 1자에서 64자까지 가능")
    val title: String,

    @field:Size(min = 1, max = 128, message = "게임 제목은 최소 1자에서 최대 128자까지 가능")
    val gameTitle: String,

    @field:Size(min = 10, max = 256, message = "소개 글은 최소 10자에서 최대 256자까지 가능")
    val introduction: String,

    val alias: String
)