package com.example.kira.service.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtServerAuthConverter : AuthenticationConverter {

    override fun convert(request: HttpServletRequest): Authentication? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authHeader.startsWith("Bearer ")) BearerToken(authHeader.substring(7)) else null
    }
}

@Component
class JwtAuthManager(
    private val jwtService: JwtService,
    private val authService: AuthService
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication as BearerToken

        try {
            return validate(token)
        } catch (e: IllegalArgumentException) {
            throw InvalidBearerToken(e.message)
        }
    }


    private fun validate(token: BearerToken): Authentication {
        val username = jwtService.getUsername(token)
        val user = authService.loadUserByUsername(username)

        if (jwtService.isValid(token, user)) {
            return UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities)
        }

        throw IllegalArgumentException("Token is not valid.")
    }

}

class InvalidBearerToken(message: String?): AuthenticationException(message)