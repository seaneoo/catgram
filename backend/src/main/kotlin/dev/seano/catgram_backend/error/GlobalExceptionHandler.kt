package dev.seano.catgram_backend.error

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException

@Suppress("unused")
@RestControllerAdvice
class GlobalExceptionHandler(private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)) {

	@ExceptionHandler(Exception::class)
	fun handleGenericException(req: HttpServletRequest, e: Exception): ResponseEntity<ExceptionResponse> {
		val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = e.message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}

	@ExceptionHandler(DataIntegrityViolationException::class)
	fun handleDataIntegrityViolationException(
		req: HttpServletRequest, e: DataIntegrityViolationException
	): ResponseEntity<ExceptionResponse> {
		var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
		var message = e.message

		if (e.rootCause is SQLException) {
			val sqlException = e.rootCause as SQLException
			if (sqlException.sqlState == "23505") {
				httpStatus = HttpStatus.BAD_REQUEST
				message = "Username already exists"
			}
		}

		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}

	@ExceptionHandler(BadCredentialsException::class)
	fun handleBadCredentialsException(
		req: HttpServletRequest, e: BadCredentialsException
	): ResponseEntity<ExceptionResponse> {
		val httpStatus = HttpStatus.BAD_REQUEST
		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = e.message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}

	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(req: HttpServletRequest, e: NotFoundException): ResponseEntity<ExceptionResponse> {
		val httpStatus = HttpStatus.NOT_FOUND
		val response = ExceptionResponse(
			statusCode = httpStatus.value(),
			statusReason = httpStatus.reasonPhrase,
			path = req.requestURI,
			message = e.message
		)
		return ResponseEntity.status(httpStatus).body(response)
	}
}
