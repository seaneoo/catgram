package dev.seano.catgram_backend.feature.auth

import dev.seano.catgram_backend.error.BadCredentialsException
import dev.seano.catgram_backend.error.MissingParametersException
import dev.seano.catgram_backend.error.PasswordsDoNotMatchException
import dev.seano.catgram_backend.error.UserNotFoundException
import dev.seano.catgram_backend.feature.auth.model.LoginPayload
import dev.seano.catgram_backend.feature.auth.model.RegistrationPayload
import dev.seano.catgram_backend.feature.user.UserProfile
import dev.seano.catgram_backend.security.JwtService
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
		if (payload.password.isBlank()) throw MissingParametersException("Password must not be blank")

		if (payload.password != payload.passwordVerify) throw PasswordsDoNotMatchException()
		val hashedPassword = passwordEncoder.encode(payload.password)

		val userAuth = UserAuth(username = payload.username.lowercase(), password = hashedPassword)
		val userProfile = UserProfile(user = userAuth)
		userAuth.profile = userProfile
		return userAuthRepository.save(userAuth)
	}

	@Transactional
	fun login(payload: LoginPayload): String {
		val user = userAuthRepository.findByUsername(payload.username.lowercase()) ?: throw UserNotFoundException()
		if (!passwordEncoder.matches(
				payload.password, user.password
			)
		) throw BadCredentialsException("Incorrect username or password.")

		val token = jwtService.generateToken(user)
			?: throw RuntimeException("Could not generate a token. If this issue persists, please contact support.")
		return token
	}
}
