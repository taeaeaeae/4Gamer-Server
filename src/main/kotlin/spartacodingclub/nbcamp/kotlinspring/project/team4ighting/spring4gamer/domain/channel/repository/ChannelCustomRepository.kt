package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository

import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post

interface ChannelCustomRepository {

    fun findTopPosts(channelId: Long): List<Post>
}