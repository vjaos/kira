package com.example.kira.service.auth

import com.example.kira.service.auth.dto.InvalidBearerToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthManager(
    private val jwtService: JwtService,
    private val authService: AuthService
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
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
