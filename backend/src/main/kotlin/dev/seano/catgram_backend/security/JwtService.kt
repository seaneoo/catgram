package dev.seano.catgram_backend.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
	@Value("\${security.jwt.secret-key}") private val secretKey: String,
	@Value("\${security.jwt.expiration-time}") private val expiration: Long
) {

	private fun getSecretSigningKey(): SecretKey {
		val key = Decoders.BASE64.decode(secretKey)
		return Keys.hmacShaKeyFor(key)
	}

	private fun extractAllClaims(token: String): Claims {
		return Jwts.parser().verifyWith(getSecretSigningKey()).build().parseSignedClaims(token).payload
	}

	private fun <T : Any> extractClaim(token: String, resolver: (Claims) -> T): T {
		val claims = extractAllClaims(token)
		return resolver(claims)
	}

	fun extractSubject(token: String): String? {
		return extractClaim(token, Claims::getSubject)
	}

	fun extractExpiration(token: String): Date? {
		return extractClaim(token, Claims::getExpiration)
	}

	fun generateToken(userDetails: UserDetails): String? {
		val currentMs = System.currentTimeMillis()
		val issuedAt = Date(currentMs)
		val expiresAt = Date(currentMs + expiration)
		val token = Jwts.builder().subject(userDetails.username).issuedAt(issuedAt).expiration(expiresAt)
			.signWith(getSecretSigningKey()).compact()
		return token
	}

	fun isExpired(token: String): Boolean {
		return extractExpiration(token)?.before(Date(System.currentTimeMillis())) ?: false
	}

	fun isValid(token: String, userDetails: UserDetails): Boolean {
		val subject = extractSubject(token)
		return subject.equals(userDetails.username) && !isExpired(token)
	}
}
