package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
