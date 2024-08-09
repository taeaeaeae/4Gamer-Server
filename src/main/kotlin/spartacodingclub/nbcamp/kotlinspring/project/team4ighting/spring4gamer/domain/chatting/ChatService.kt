package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.chatting

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    fun save(sessionId: String, memberId: String) {
        redisTemplate.opsForHash<String, String>().put("CHAT_SESSIONS", sessionId, memberId)
    }


    fun isExistsMember(memberId: String): Boolean =

        redisTemplate
            .opsForHash<String, String>()
            .entries("CHAT_SESSIONS")
            .containsValue(memberId)


    fun delete(sessionId: String) {
        redisTemplate.opsForHash<String, String>().delete("CHAT_SESSIONS", sessionId)
    }
}