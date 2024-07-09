package dev.seano.catgram_backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableMBeanExport
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Suppress("unused")
@Configuration
@EnableWebSecurity
@EnableMBeanExport
class SecurityFilter(
	private val authenticationProvider: AuthenticationProvider, private val jwtAuthFilter: JwtAuthFilter
) {

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		//@formatter:off
		return http
			.csrf {
				it.disable()
			}
			.authorizeHttpRequests {
				it.requestMatchers("/v1/auth/**").permitAll().anyRequest().authenticated()
			}
			.sessionManagement {
				it.sessionCreationPolicy(STATELESS)
			}
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
		//@formatter:on
	}
}
