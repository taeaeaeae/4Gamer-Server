//package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config.websocket
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.socket.config.annotation.EnableWebSocket
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
//import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config.websocket.handler.AlertWebSocketHandler
//
//@Configuration
//@EnableWebSocket
//class WebSocketConfig(
//    private val webSocketHandler: AlertWebSocketHandler
//): WebSocketConfigurer {
//
//    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
//
//        registry.addHandler(webSocketHandler, "/ws")
//            .setAllowedOriginPatterns("*")
//    }
//}