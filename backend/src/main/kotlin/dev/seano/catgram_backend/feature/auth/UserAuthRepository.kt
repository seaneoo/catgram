package dev.seano.catgram_backend.feature.auth

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAuthRepository : CrudRepository<UserAuth, Int> {

	fun findByUsername(username: String?): UserAuth?
}
