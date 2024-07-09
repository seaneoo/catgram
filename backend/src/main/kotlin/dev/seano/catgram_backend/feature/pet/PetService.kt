package dev.seano.catgram_backend.feature.pet

import dev.seano.catgram_backend.feature.pet.model.CreatePetPayload
import dev.seano.catgram_backend.feature.user.UserProfile
import org.springframework.stereotype.Service

@Service
class PetService(private val petRepository: PetRepository) {

	fun create(createPetPayload: CreatePetPayload, userProfile: UserProfile): Pet {
		val pet = Pet(name = createPetPayload.name, species = createPetPayload.species, user = userProfile)
		return petRepository.save(pet)
	}
}
