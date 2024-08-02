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
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.JwtHelper

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메시지 처리 활성화(stomp)
class StompWebSocketConfig(
    private val jwtHelper: JwtHelper
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

                        jwtHelper.validateToken(token)
                            .onFailure {
                                throw IllegalArgumentException("Invalid token")
                            }
                    }

                    return message
                }
            }
        )
    }
}