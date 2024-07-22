package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response

class OAuth2Response(
    private val attribute: Map<String, Any>
) {
    val provider = "google"

    val providerId = attribute["sub"].toString()

    val email = attribute["email"].toString()

    val name = attribute["name"].toString()
}