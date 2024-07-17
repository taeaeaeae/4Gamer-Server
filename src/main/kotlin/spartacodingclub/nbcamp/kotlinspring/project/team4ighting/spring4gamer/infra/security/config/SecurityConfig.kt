package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.CustomAuthenticationEntryPoint
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.jwtAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: jwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/**")
                    .permitAll()
                it.requestMatchers(
                    "/api/v1/auth/signin",
                    "/api/v1/auth/signup",
                    "/api/v1/members/{id}"
                )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .build()
    }
}