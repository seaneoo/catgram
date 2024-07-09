package dev.seano.catgram_backend.security

import dev.seano.catgram_backend.auth.UserAuthRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Suppress("unused")
@Configuration
class SecurityConfig(
	@Value("\${security.argon2.salt-length}") private val saltLength: Int,
	@Value("\${security.argon2.hash-length}") private val hashLength: Int,
	@Value("\${security.argon2.parallelism}") private val parallelism: Int,
	@Value("\${security.argon2.memory}") private val memory: Int,
	@Value("\${security.argon2.iterations}") private val iterations: Int,
	private val userAuthRepository: UserAuthRepository
) {

	@Bean
	fun authenticationProvider(): AuthenticationProvider {
		val provider = DaoAuthenticationProvider()
		provider.setUserDetailsService(userDetailsService())
		provider.setPasswordEncoder(passwordEncoder())
		return provider
	}

	@Bean
	fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
		return authenticationConfiguration.authenticationManager
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations)
	}

	@Bean
	fun userDetailsService(): UserDetailsService {
		return UserDetailsService { userAuthRepository.findByUsername(it) }
	}
}
