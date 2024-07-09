package dev.seano.catgram_backend.feature.pet

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : CrudRepository<Pet, Int>
