package com.example.kira.service.auth

import com.example.kira.configuration.AuthProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.aspectj.weaver.bcel.BcelShadow
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtService(private val authProperties: AuthProperties) {

    private val key = Keys.hmacShaKeyFor(authProperties.secretKey.toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun generateToken(username: String): BearerToken {
        val tokenBuilder = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
            .signWith(key)

        return BearerToken(tokenBuilder.compact())
    }

    fun getUsername(token: BearerToken): String {
        return parser.parseClaimsJws(token.value).body.subject
    }


    fun isValid(token: BearerToken, user: UserDetails?): Boolean {
        val claims = parser.parseClaimsJws(token.value).body
        val unexpired = claims.expiration.after(Date.from(Instant.now()))

        return unexpired && (claims.subject == user?.username)
    }
}
