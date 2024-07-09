package dev.seano.catgram_backend.feature.auth

import dev.seano.catgram_backend.feature.auth.model.LoginPayload
import dev.seano.catgram_backend.feature.auth.model.RegistrationPayload
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@RequestMapping("/v1/auth")
class UserAuthController(private val userAuthService: UserAuthService) {

	@PostMapping("/register")
	fun register(@RequestBody payload: RegistrationPayload): ResponseEntity<Any> {
		userAuthService.register(payload)
		return ResponseEntity.status(HttpStatus.CREATED).build()
	}

	@PostMapping("/login")
	fun login(@RequestBody payload: LoginPayload): ResponseEntity<HashMap<String, String>> {
		val token = userAuthService.login(payload)
		return ResponseEntity.ok(hashMapOf("token" to token))
	}
}
