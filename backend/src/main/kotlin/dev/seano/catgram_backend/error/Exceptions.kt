package dev.seano.catgram_backend.error

open class NotFoundException(override val message: String? = null) : Exception(message)

class UserNotFoundException(override val message: String? = "User not found") : NotFoundException(message)

open class BadRequestException(override val message: String? = null) : RuntimeException(message)

class BadCredentialsException(override val message: String? = "") : BadRequestException(message)

class PasswordsDoNotMatchException(override val message: String? = "Passwords do not match") :
	BadRequestException(message)
