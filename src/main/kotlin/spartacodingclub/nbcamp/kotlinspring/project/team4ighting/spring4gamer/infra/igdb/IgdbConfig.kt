package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class IgdbConfig {

    @Bean
    fun restTemplate(): RestTemplate =

        RestTemplate()
}