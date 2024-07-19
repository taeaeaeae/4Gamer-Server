package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception

data class CustomAccessDeniedException (
    override val message: String
) : RuntimeException(message)