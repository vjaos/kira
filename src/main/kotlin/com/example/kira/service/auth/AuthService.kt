package com.example.kira.service.auth

import com.example.kira.dto.Jwt
import com.example.kira.dto.Login
import com.example.kira.dto.UserCreateRequest
import com.example.kira.entity.User
import com.example.kira.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val jwtService: JwtService
) : UserDetailsService {

    fun login(login: Login): Jwt {
        val user = userRepository.findByUsername(login.username)

        user?.let {
            if (encoder.matches(login.password, user.password)) {
                return Jwt(jwtService.generateToken(it.username).value)
            }
        }

        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    fun register(request: UserCreateRequest) {
        val user = userRepository.findByUsername(request.username)
        if (user != null) {
            throw IllegalArgumentException()
        } else {
            userRepository.save(
                User(
                    username = request.username,
                    password = encoder.encode(request.password)
                )
            )
        }

    }

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username)
            ?.let { UserAuthDetails(it) }
            ?: throw UsernameNotFoundException("User $username not found")
}
