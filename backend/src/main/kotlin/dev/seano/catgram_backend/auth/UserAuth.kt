package dev.seano.catgram_backend.auth

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.seano.catgram_backend.user.UserProfile
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "user_auth")
@EntityListeners(AuditingEntityListener::class)
data class UserAuth(
	@Id @GeneratedValue(strategy = IDENTITY) val id: Int? = null,
	@Column(nullable = false, unique = true) @JvmField val username: String,
	@Column(nullable = false) @JvmField val password: String,
	@CreationTimestamp @Column(name = "created_at") val createdAt: Instant? = null,
	@UpdateTimestamp @Column(name = "updated_at") val updatedAt: Instant? = null,
	@OneToOne(cascade = [ALL], mappedBy = "auth") @JsonIgnore var profile: UserProfile? = null
) : UserDetails {

	override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

	override fun getPassword() = password

	override fun getUsername() = username
}
