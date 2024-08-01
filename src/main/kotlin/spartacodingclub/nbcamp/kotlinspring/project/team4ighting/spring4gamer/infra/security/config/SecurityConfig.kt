package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.OAuth2Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt.jwtAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: jwtAuthenticationFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val customOAuth2UserService: OAuth2Service,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =

        http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/**")
                    .permitAll()
                it.requestMatchers(
                    "/api/v1/auth/signin",
                    "/api/v1/auth/signup",
                    "/api/v1/recaptcha"
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
                it.defaultSuccessUrl("/api/v1/auth/signin/google")
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

}