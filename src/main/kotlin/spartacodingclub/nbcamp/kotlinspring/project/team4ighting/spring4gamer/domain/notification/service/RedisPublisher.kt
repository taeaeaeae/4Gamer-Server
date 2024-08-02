package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.dto.MessageSubResponse

@Service
class RedisPublisher(
    private val channelTopic: ChannelTopic,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun publish(message: MessageSubResponse) =

        redisTemplate.convertAndSend(channelTopic.topic, message)
}