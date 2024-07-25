package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.s3.fileupload.dto.response

data class S3GetResponseDto(val fileNames: List<String>) {
    companion object {
        fun from(
            fileNames: List<String>
        ): S3GetResponseDto {
            return S3GetResponseDto(fileNames)
        }
    }
}
