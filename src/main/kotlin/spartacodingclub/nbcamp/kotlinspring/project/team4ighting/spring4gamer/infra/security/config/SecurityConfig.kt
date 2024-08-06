package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.OAuth2Response
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.CustomOAuth2User
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.OAuth2Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.jwtAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: jwtAuthenticationFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val customOAuth2UserService: OAuth2Service
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =

        http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) } // cors 설정
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/**")
                    .permitAll()
                it.requestMatchers(
                    "/api/v1/auth/signin",
                    "/api/v1/auth/signup",
                    "/api/v1/recaptcha",
                    "/api/v1/**" // 테스트 확인 용
                )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            .oauth2Login {
                it.loginProcessingUrl("/google")
                it.userInfoEndpoint { u -> u.userService(customOAuth2UserService) }
                it.successHandler { request, response, authentication ->
                    response.sendRedirect(customOAuth2UserService.googleSignin(authentication))
//                    response.writer.write(customOAuth2UserService.googleSignin(authentication))
                }
                it.failureHandler { request, response, exception ->
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.contentType = "application/json"
                    response.writer.write("{\"error\":\"${exception.message}\"}")
                }
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .build()


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.setAllowedOriginPatterns(listOf("*"))
        configuration.allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.addExposedHeader("Set-Cookie")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
