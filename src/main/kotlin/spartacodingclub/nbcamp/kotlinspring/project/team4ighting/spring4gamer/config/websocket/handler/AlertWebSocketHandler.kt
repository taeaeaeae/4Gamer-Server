package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config.websocket.handler

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config.websocket.WebSocketConfig
import java.util.concurrent.ConcurrentHashMap

@Component
class AlertWebSocketHandler : TextWebSocketHandler() {

    private val logger = LoggerFactory.getLogger(WebSocketConfig::class.java)
    private val sessionMap = ConcurrentHashMap<String, WebSocketSession>()


    //websocket handshake가 완료되어 연결이 수립될 때 호출
    override fun afterConnectionEstablished(session: WebSocketSession) {

        logger.info("Connection established: {}", session)

        sessionMap[session.id] = session
        logger.info("sessionMap : {}", sessionMap)
    }


    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {

        logger.info("Connection closed: {}, {}", session, closeStatus)

        sessionMap.remove(session.id)
        logger.info("sessionMap : {}", sessionMap)
    }
}