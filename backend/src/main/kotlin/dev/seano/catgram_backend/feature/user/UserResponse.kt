package dev.seano.catgram_backend.feature.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class UserResponse(
	val id: Int,
	val username: String,
	@JsonProperty("created_at") val createdAt: Instant,
	val displayName: String? = null,
	val bio: String? = null,
	val gender: String? = null,
	val pronouns: String? = null,
)
