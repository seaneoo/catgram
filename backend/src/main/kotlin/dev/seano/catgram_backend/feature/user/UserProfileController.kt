package dev.seano.catgram_backend.feature.user

import dev.seano.catgram_backend.feature.auth.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@RequestMapping("/v1/user")
class UserProfileController {

	@GetMapping("/me")
	fun me(@AuthenticationPrincipal userAuth: UserAuth): ResponseEntity<UserResponse> {
		return ResponseEntity.ok(userAuth.profile?.response())
	}
}
