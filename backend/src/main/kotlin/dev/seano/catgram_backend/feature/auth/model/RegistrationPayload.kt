package dev.seano.catgram_backend.feature.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationPayload(
	val username: String, val password: String, @JsonProperty("password_verify") val passwordVerify: String
)
