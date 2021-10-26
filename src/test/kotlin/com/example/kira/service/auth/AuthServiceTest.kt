package com.example.kira.service.auth

import com.example.kira.dto.Login
import com.example.kira.entity.User
import com.example.kira.repository.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.server.ResponseStatusException

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @InjectMocks
    lateinit var authService: AuthService
    @Mock
    lateinit var userRepository: UserRepository
    @Mock
    lateinit var encoder: PasswordEncoder
    @Mock
    lateinit var jwtService: JwtService


    @Test
    fun `when user with give username not found throw exception`() {
        assertThrows<ResponseStatusException> {
            authService.login(Login("user", "pass"))
        }
    }

    @Test
    fun `when user found but password is wrong - throw exception`() {
        whenever(userRepository.findByUsername(anyString()))
            .thenReturn(User("user", "password"))
        whenever(encoder.matches(any(), anyString()))
            .thenReturn(false)

        assertThrows<ResponseStatusException> {
            authService.login(Login("user", "pass"))
        }
    }
}