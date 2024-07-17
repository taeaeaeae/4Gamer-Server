package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.dto

data class CreateChannelRequest(
    val title: String,
    val gameTitle: String,
    val introduction: String,
    val alias: String,
)