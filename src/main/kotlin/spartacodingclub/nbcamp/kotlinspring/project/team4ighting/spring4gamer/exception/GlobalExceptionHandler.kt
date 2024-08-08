package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.dto.ErrorResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))


    @ExceptionHandler(CustomAccessDeniedException::class)
    fun handleCustomAccessDeniedException(e: CustomAccessDeniedException): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(e.message))


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        bindingResult: BindingResult
    ): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(bindingResult.fieldError?.defaultMessage))


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message))


    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))

}