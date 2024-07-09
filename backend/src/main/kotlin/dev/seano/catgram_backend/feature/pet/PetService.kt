package dev.seano.catgram_backend.feature.pet

import dev.seano.catgram_backend.feature.pet.model.CreatePetPayload
import dev.seano.catgram_backend.feature.user.UserProfile
import org.springframework.stereotype.Service

@Service
class PetService(private val petRepository: PetRepository) {

	fun findAllByUsername(userProfile: UserProfile): List<Pet> {
		return petRepository.findAllByUsername(userProfile.user.username)
	}

	fun findByNameAndUsername(petName: String, userProfile: UserProfile): List<Pet> {
		return petRepository.findByNameAndUsername(petName, userProfile.user.username)
	}

	fun create(createPetPayload: CreatePetPayload, userProfile: UserProfile): Pet {
		val pet = Pet(name = createPetPayload.name, species = createPetPayload.species, user = userProfile)
		return petRepository.save(pet)
	}
}
