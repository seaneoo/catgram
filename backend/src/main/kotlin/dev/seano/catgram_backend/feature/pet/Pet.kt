package dev.seano.catgram_backend.feature.pet

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.seano.catgram_backend.feature.pet.model.PetResponse
import dev.seano.catgram_backend.feature.user.UserProfile
import jakarta.persistence.*
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table
@EntityListeners(AuditingEntityListener::class)
data class Pet(
	@Id @GeneratedValue(strategy = IDENTITY) val id: Int? = null,
	@ManyToOne(fetch = EAGER) @JoinColumn(name = "user_id") @JsonIgnore val user: UserProfile,
	@Column(nullable = false) val name: String,
	@Column(nullable = false) val species: String,
	@CreationTimestamp @Column(name = "created_at") val createdAt: Instant? = null,
	@UpdateTimestamp @Column(name = "updated_at") val updatedAt: Instant? = null,
) {

	fun response() = PetResponse(id = id!!, name = name, species = species)
}
