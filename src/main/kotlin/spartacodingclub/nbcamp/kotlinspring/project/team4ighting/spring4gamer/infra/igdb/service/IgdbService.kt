package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb.service

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.igdb.SaveAccessToken

@Service
class IgdbService(
    @Value("\${twitch.client-id}")
    private val clientId: String,

    @Value("\${twitch.client-secret}")
    private val clientSecret: String,

    @Value("https://id.twitch.tv/oauth2/token")
    private val tokenUrl: String,

    private val restTemplate: RestTemplate,

    private val saveAccessToken: SaveAccessToken
) {

    fun getAccessToken(): String? {
        val requestBody = mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "grant_type" to "client_credentials"
        )

        val response = restTemplate.postForEntity(tokenUrl, requestBody, JsonNode::class.java)

        val token = response.body?.get("access_token")?.asText()

        token?.let { saveAccessToken.accessToken = it }

        return token
    }

    fun searchGamesByName(
        token: String,
        gameName: String
    ): ResponseEntity<String> {

        try {
            val headers = HttpHeaders().apply {
                set("Client-ID", clientId)
                set("Authorization", "Bearer $token")
            }
            val query = "search $gameName; fields name;"
            val entity = HttpEntity<String>(query, headers)
            val response =
                restTemplate.exchange(
                    "https://api.igdb.com/v4/games",
                    HttpMethod.POST,
                    entity,
                    String::class.java
                )
            println("Response: ${response.body}") // 개발용
            return response
        } catch (ex: RestClientException) {
            throw RestClientException("REST request failed", ex)
        }
    }

    fun checkGamesName(
        token: String,
        gameName: String
    ): Boolean {

        try {
            val headers = HttpHeaders().apply {
                set("Client-ID", clientId)
                set("Authorization", "Bearer $token")
            }
            val query = "search $gameName; fields name;"
            val entity = HttpEntity<String>(query, headers)
            val response =
                restTemplate.exchange(
                    "https://api.igdb.com/v4/games",
                    HttpMethod.POST,
                    entity,
                    String::class.java
                )
            return response.body?.contains(gameName) ?: false
        } catch (ex: RestClientException) {
            throw RestClientException("REST request failed", ex)
        }
    }

    fun getGamesById(
        token: String,
        gameId: String
    ): ResponseEntity<String> {

        try {
            val headers = HttpHeaders().apply {
                set("Client-ID", clientId)
                set("Authorization", "Bearer $token")
            }
            val query = "fields *; where id = $gameId;"
            val entity = HttpEntity<String>(query, headers)
            val response =
                restTemplate.exchange(
                    "https://api.igdb.com/v4/games",
                    HttpMethod.POST,
                    entity,
                    String::class.java
                )
            println("Response: ${response.body}") // 개발용
            return response
        } catch (ex: RestClientException) {
            throw RestClientException("REST request failed", ex)
        }
    }

    fun getTopGames(
        token: String,
    ): ResponseEntity<String> {

        try {
            val headers = HttpHeaders().apply {
                set("Client-ID", clientId)
                set("Authorization", "Bearer $token")
                set("content-type", "text/plain")
            }

            val query = "fields name, total_rating, total_rating_count; where total_rating_count >= 1000; sort rating desc; limit 10; "

            val entity = HttpEntity<String>(query, headers)

            val response = restTemplate.exchange(
                "https://api.igdb.com/v4/games/",
                HttpMethod.POST,
                entity,
                String::class.java
            )

            println("Response: ${response.body}") // 개발용
            return response
        } catch (ex: RestClientException) {
            throw RestClientException("REST request failed", ex)
        }
    }
}