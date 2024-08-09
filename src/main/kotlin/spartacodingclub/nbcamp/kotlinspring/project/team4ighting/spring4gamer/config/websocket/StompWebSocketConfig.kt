package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.chatting.ChatService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.PublishType
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.dto.MessageSubResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.notification.service.RedisPublisher
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.JwtHelper
import java.time.ZonedDateTime
import java.util.*

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메시지 처리 활성화(stomp)
class StompWebSocketConfig(
    private val jwtHelper: JwtHelper,
    private val chatService: ChatService,
    private val publisher: RedisPublisher
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/sub") // 인메모리 메시지 브로커를 설정
        registry.setApplicationDestinationPrefixes("/pub") // 수신 메시지를 @MessageMapping 메서드에 매핑
    }


    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/ws") // http://localhost:8080/ws
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }


    // 연결 요청 시 STOMP 헤더에 JWT 토큰이 있는지 체크
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(
            object : ChannelInterceptor {

                override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {

                    val accessor = StompHeaderAccessor.wrap(message)

                    if (StompCommand.CONNECT == accessor.command) {
                        val token = accessor.getFirstNativeHeader("token")
                            ?: throw IllegalArgumentException("Invalid token")

                        val targetId = accessor.getFirstNativeHeader("targetId")
                        var roomId = accessor.getFirstNativeHeader("roomId")

                        jwtHelper.validateToken(token)
                            .onSuccess { claims ->
                                targetId?.let {
                                    accessor.sessionId?.let { id -> chatService.save(id, claims.payload.subject) }
                                    publisher.publish(
                                        MessageSubResponse(
                                            type = PublishType.NOTIFICATION,
                                            subjectId = claims.payload.subject,
                                            targetId = targetId,
                                            message = "${targetId}님이 채팅 요청을 보냈습니다.",
                                            createdAt = ZonedDateTime.now(),
                                            roomId = roomId
                                        )
                                    )
                                }
                            }
                            .onFailure {
                                throw IllegalArgumentException("Invalid token")
                            }
                    }

                    if (StompCommand.DISCONNECT == accessor.command) { // 연결을 종료할 때는 JWT 토큰이 없음
                        accessor.sessionId?.let { id -> chatService.delete(id) }
                    }

                    return message
                }
            }
        )
    }
}