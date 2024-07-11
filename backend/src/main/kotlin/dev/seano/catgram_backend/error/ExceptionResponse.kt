package dev.seano.catgram_backend.error

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExceptionResponse(
	@JsonProperty("status_code") val statusCode: Int,
	@JsonProperty("status_reason") val statusReason: String,
	val path: String? = null,
	val timestamp: Instant? = Instant.now(),
	val message: String? = null,
)
