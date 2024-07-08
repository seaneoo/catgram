package dev.seano.catgram_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CatgramBackendApplication

fun main(args: Array<String>) {
	runApplication<CatgramBackendApplication>(*args)
}
