package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.PublishType
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.dto.MessageSubResponse

@Service
class RedisSubscriber(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val messageTemplate: SimpMessageSendingOperations
) : MessageListener {

    private val logger = LoggerFactory.getLogger(RedisSubscriber::class.java)


    override fun onMessage(message: Message, pattern: ByteArray?) {

        runCatching {

            val publishMessage = redisTemplate.stringSerializer.deserialize(message.body)
            val message = objectMapper.readValue(publishMessage, MessageSubResponse::class.java)

            when (message.type) {
                PublishType.NOTIFICATION -> messageTemplate
                    .convertAndSend(
                        "/sub/notification/${message.targetId}",
                        message
                    )

                PublishType.CHAT -> messageTemplate
                    .convertAndSend(
                        "/sub/chat/${message.roomId}",
                        message
                    )
            }

        }.onFailure {

            logger.error("Exception: {}", it.message)
        }
    }
}