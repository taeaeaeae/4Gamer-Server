package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception

data class ModelNotFoundException(
    val modelName: String,
    val id: Any
) : RuntimeException("Model $modelName not found with given id: $id")