package dev.seano.catgram_backend.feature.pet

import dev.seano.catgram_backend.feature.auth.UserAuth
import dev.seano.catgram_backend.feature.pet.model.CreatePetPayload
import dev.seano.catgram_backend.feature.pet.model.PetResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Suppress("unused")
@RestController
@RequestMapping("/v1/pets")
class PetController(private val petService: PetService) {

	@GetMapping
	fun findPets(
		@RequestParam(required = false) name: String? = null, @AuthenticationPrincipal userAuth: UserAuth
	): ResponseEntity<List<PetResponse>> {
		val userProfile = userAuth.profile ?: throw Exception("User not found")
		val pets = if (name == null) {
			petService.findAllByUsername(userProfile).map { it.response() }
		} else {
			petService.findByNameAndUsername(name, userProfile).map { it.response() }
		}
		return ResponseEntity.ok(pets)
	}

	@PostMapping
	fun createPetForUser(
		@RequestBody createPetPayload: CreatePetPayload, @AuthenticationPrincipal userAuth: UserAuth
	): ResponseEntity<Any> {
		val userProfile = userAuth.profile ?: throw Exception("User not found")
		petService.create(createPetPayload, userProfile)
		return ResponseEntity.status(HttpStatus.CREATED).build()
	}
}
