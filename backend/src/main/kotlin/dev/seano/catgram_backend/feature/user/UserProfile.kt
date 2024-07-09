package dev.seano.catgram_backend.feature.user

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.seano.catgram_backend.feature.auth.UserAuth
import dev.seano.catgram_backend.feature.pet.Pet
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.EAGER

@Entity
@Table(name = "user_profile")
data class UserProfile(
	@Id val id: Int? = null,
	@OneToOne @MapsId @JsonIgnore val user: UserAuth,
	@Column(nullable = true) val bio: String? = null,
	@Column(nullable = true) val gender: String? = null,
	@Column(nullable = true) val pronouns: String? = null,
	@OneToMany(fetch = EAGER, cascade = [ALL], mappedBy = "user") @JsonIgnore var pets: List<Pet>? = null
) {

	fun response() = UserResponse(
		id = id!!,
		username = user.username,
		createdAt = user.createdAt!!,
		bio = bio,
		gender = gender,
		pronouns = pronouns
	)
}
