package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.s3.fileupload.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.s3.fileupload.dto.S3GetResponseDto
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.s3.fileupload.service.FileUploadService
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.security.MemberPrincipal

@RestController
@RequestMapping("/api/v1")
class FileUploadController(
    private val fileUploadService: FileUploadService,
) {

    // presignedURL 받아오기
    @GetMapping("/presigned")
    fun getPreSignedUrl(
        @AuthenticationPrincipal member: MemberPrincipal,
        @RequestParam file: String,
    ): ResponseEntity<String> =

        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(fileUploadService.preSignedUrl(file))

    // 이미지 단건 조회
    @GetMapping("/images")
    fun getFile(
        @AuthenticationPrincipal member: MemberPrincipal,
        @RequestParam file: String,
    ) : ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(fileUploadService.getFile(file))

    // 이미지 전체 조회
    @GetMapping("/{bucket}/**")
    fun find(
        @PathVariable bucket: String,
        request: HttpServletRequest
    ): ResponseEntity<S3GetResponseDto> {
        val split = request.requestURI.split("/s3/$bucket")
        val prefix = if (split.size < 2) "" else split[1].substring(1)
        val s3GetResponseDto = fileUploadService.find(bucket, prefix)
        return ResponseEntity.ok(s3GetResponseDto)
    }

    // 이미지 삭제
    @DeleteMapping("/images")
    fun deleteImage(
        @AuthenticationPrincipal member: MemberPrincipal,
        @RequestParam file: String,
    ): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.OK).body(fileUploadService.delete(file))

}