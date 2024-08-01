package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.recaptcha

data class RecaptchaResponse(
    val success: Boolean,
    val challenge_ts: String?,
    val hostname: String?,
    val score: Double, // 0.5 <
    val action: String,
    val errorCodes: List<String>?
)