package com.example.kira.service.auth.converter

import com.example.kira.service.auth.BearerToken
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtAuthConverter : AuthenticationConverter {

    override fun convert(request: HttpServletRequest): Authentication? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authHeader.startsWith("Bearer ")) BearerToken(authHeader.substring(7)) else null
    }
}