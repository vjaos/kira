package com.example.kira.service.auth

import com.example.kira.configuration.AuthProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtService(authProperties: AuthProperties) {

    private val key = Keys.hmacShaKeyFor(authProperties.secretKey.toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun generateToken(username: String): BearerToken {
        val tokenBuilder = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(36500, ChronoUnit.DAYS)))
            .signWith(key)
        return BearerToken(tokenBuilder.compact())
    }

    fun getUsername(token: BearerToken): String {
        return parser.parseClaimsJws(token.value).body.subject
    }


    fun isValid(token: BearerToken, user: UserDetails?): Boolean {
        return try {
            val claims = parser.parseClaimsJws(token.value).body

            claims.subject == user?.username
        } catch (ex: ExpiredJwtException) {
            false
        }
    }
}
