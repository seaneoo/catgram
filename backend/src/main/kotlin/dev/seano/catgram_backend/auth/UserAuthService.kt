package dev.seano.catgram_backend.auth

import dev.seano.catgram_backend.auth.model.LoginPayload
import dev.seano.catgram_backend.auth.model.RegistrationPayload
import dev.seano.catgram_backend.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthService(
	private val userAuthRepository: UserAuthRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService
) {

	fun register(payload: RegistrationPayload): UserAuth {
		val hashedPassword = passwordEncoder.encode(payload.password)
		val user = UserAuth(username = payload.username.lowercase(), password = hashedPassword)
		return userAuthRepository.save(user)
	}

	fun login(payload: LoginPayload): String {
		val user = userAuthRepository.findByUsername(payload.username.lowercase()) ?: throw Exception("User not found")
		if (!passwordEncoder.matches(payload.password, user.password)) throw Exception("Bad credentials")

		val token = jwtService.generateToken(user)
			?: throw Exception("Could not generate a token. If this issue persists, please contact support.")
		return token
	}
}
