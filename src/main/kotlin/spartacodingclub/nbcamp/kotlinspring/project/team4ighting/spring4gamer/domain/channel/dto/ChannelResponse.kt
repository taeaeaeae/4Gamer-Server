package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto

data class ChannelResponse(
    val id: Long,
    val title: String,
    val gameTitle: String,
    val introduction: String,
    val alias: String,
)
