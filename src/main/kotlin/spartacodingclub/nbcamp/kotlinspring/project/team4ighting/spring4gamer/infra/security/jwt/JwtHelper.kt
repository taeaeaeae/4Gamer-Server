package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.jwt


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtHelper(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.accessTokenSecret}") private val accessTokenSecret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Int,
) {

    fun validateToken(jwt: String): Result<Jws<Claims>> =

        kotlin.runCatching {
            Keys.hmacShaKeyFor(accessTokenSecret.toByteArray())
                .let {
                    Jwts.parser()
                        .verifyWith(it)
                        .build()
                        .parseSignedClaims(jwt)
                }
        }


    fun generateAccessToken(
        subject: String,
        email: String,
        role: String
    ): String =

        generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour.toLong()))


    private fun generateToken(
        subject: String,
        email: String,
        role: String,
        expirationPeriod: Duration
    ): String =

        Instant.now().let { now ->
            Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationPeriod)))
                .claims(
                    Jwts.claims()
                        .add( mapOf("email" to email, "role" to role) )
                        .build()
                )
                .signWith(Keys.hmacShaKeyFor(accessTokenSecret.toByteArray()))
                .compact()
        }
}