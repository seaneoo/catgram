package dev.seano.catgram_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatgramBackendApplication

fun main(args: Array<String>) {
	runApplication<CatgramBackendApplication>(*args)
}
