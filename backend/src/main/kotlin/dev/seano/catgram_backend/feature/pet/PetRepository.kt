package dev.seano.catgram_backend.feature.pet

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : CrudRepository<Pet, Int> {

	@Query("SELECT p FROM Pet p JOIN p.user u WHERE LOWER(u.user.username) = LOWER(:username)")
	fun findAllByUsername(username: String): List<Pet>

	@Query("SELECT p FROM Pet p JOIN p.user u WHERE LOWER(u.user.username) = LOWER(:username) AND LOWER(p.name) = LOWER(:petName)")
	fun findByNameAndUsername(petName: String, username: String): List<Pet>
}
