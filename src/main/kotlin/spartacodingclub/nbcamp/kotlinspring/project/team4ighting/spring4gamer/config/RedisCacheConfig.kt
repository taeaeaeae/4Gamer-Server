package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager =

        RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration
                    .defaultCacheConfig()
                    .prefixCacheNameWith(this.javaClass.packageName + ".")
                    .entryTtl(Duration.ofMinutes(1))
                    .disableCachingNullValues()
            )
            .build()

}