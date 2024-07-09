package dev.seano.catgram_backend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Service
class JwtAuthFilter(private val jwtService: JwtService, private val userDetailsService: UserDetailsService) :
	OncePerRequestFilter() {

	private val header = HttpHeaders.AUTHORIZATION
	private val scheme = "Bearer\u0020"

	override fun doFilterInternal(
		request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
	) {
		if (request.servletPath.contains("/api/auth")) {
			filterChain.doFilter(request, response)
			return
		}

		val authHeader = request.getHeader(header)
		if (authHeader == null || !authHeader.startsWith(scheme)) {
			filterChain.doFilter(request, response)
			return
		}

		val authToken = authHeader.substring(scheme.length)
		val subject = jwtService.extractSubject(authToken)

		if (subject == null || SecurityContextHolder.getContext().authentication != null) {
			filterChain.doFilter(request, response)
			return
		}

		val userDetails = userDetailsService.loadUserByUsername(subject)
		if (userDetails == null) {
			filterChain.doFilter(request, response)
			return
		}

		if (!jwtService.isValid(authToken, userDetails)) {
			filterChain.doFilter(request, response)
			return
		}

		val usernamePasswordAuthenticationToken =
			UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
		usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
		SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken

		filterChain.doFilter(request, response)
	}
}
