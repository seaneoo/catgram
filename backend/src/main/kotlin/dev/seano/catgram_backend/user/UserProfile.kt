package dev.seano.catgram_backend.user

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.seano.catgram_backend.auth.UserAuth
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "user_profile")
@EntityListeners(AuditingEntityListener::class)
data class UserProfile(
	@Id val id: Int? = null,
	@OneToOne @MapsId @JsonIgnore val auth: UserAuth,
	@Column(nullable = true) val bio: String? = null,
	@Column(nullable = true) val gender: String? = null,
	@Column(nullable = true) val pronouns: String? = null
) {

	fun response() = UserResponse(
		id = id!!,
		username = auth.username,
		createdAt = auth.createdAt!!,
		bio = bio,
		gender = gender,
		pronouns = pronouns
	)
}
