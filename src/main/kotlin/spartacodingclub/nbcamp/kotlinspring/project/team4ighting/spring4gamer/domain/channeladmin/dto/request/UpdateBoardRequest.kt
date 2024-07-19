package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.dto.request

import jakarta.validation.constraints.Size

data class UpdateBoardRequest(
    @field:Size(min = 1, max = 64, message = "게시판 제목 길이는 최소 1자에서 64자까지 가능")
    val title: String,

    @field:Size(min = 10, max = 256, message = "소개글 길이는 최소 10자에서 최대 256자까지 가능")
    val introduction: String
)