package dev.seano.catgram_backend.error

open class NotFoundException(override val message: String? = null) : Exception(message)

class UserNotFoundException(override val message: String? = "User not found") : NotFoundException(message)
