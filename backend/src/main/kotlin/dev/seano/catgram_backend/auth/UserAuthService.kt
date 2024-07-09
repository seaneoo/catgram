package dev.seano.catgram_backend.auth

import dev.seano.catgram_backend.auth.model.LoginPayload
import dev.seano.catgram_backend.auth.model.RegistrationPayload
import dev.seano.catgram_backend.security.JwtService
import dev.seano.catgram_backend.user.UserProfile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthService(
	private val userAuthRepository: UserAuthRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtService: JwtService
) {

	@Transactional
	fun register(payload: RegistrationPayload): UserAuth {
		val hashedPassword = passwordEncoder.encode(payload.password)

		val userAuth = UserAuth(username = payload.username.lowercase(), password = hashedPassword)
		val userProfile = UserProfile(auth = userAuth)
		userAuth.profile = userProfile
		return userAuthRepository.save(userAuth)
	}

	@Transactional
	fun login(payload: LoginPayload): String {
		val user = userAuthRepository.findByUsername(payload.username.lowercase()) ?: throw Exception("User not found")
		if (!passwordEncoder.matches(payload.password, user.password)) throw Exception("Bad credentials")

		val token = jwtService.generateToken(user)
			?: throw Exception("Could not generate a token. If this issue persists, please contact support.")
		return token
	}
}
