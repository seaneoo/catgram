package dev.seano.catgram_backend.auth

import jakarta.persistence.*
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
	@Id @GeneratedValue val id: Int? = null,
	@Column(nullable = false, unique = true) @JvmField val username: String,
	@Column(nullable = false) @JvmField val password: String,
	@CreationTimestamp @Column(name = "created_at") val createdAt: Instant? = null,
	@UpdateTimestamp @Column(name = "updated_at") val updatedAt: Instant? = null
) : UserDetails {

	override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

	override fun getPassword() = password

	override fun getUsername() = username
}
