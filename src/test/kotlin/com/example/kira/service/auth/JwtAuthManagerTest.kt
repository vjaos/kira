package com.example.kira.service.auth

import com.example.kira.entity.User
import com.example.kira.service.auth.dto.InvalidBearerToken
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class JwtAuthManagerTest {
    @InjectMocks
    lateinit var jwtAuthManager: JwtAuthManager

    @Mock
    lateinit var jwtService: JwtService

    @Mock
    lateinit var authService: AuthService


    @Test
    fun `when give invalid token - throw exception`() {
        whenever(jwtService.getUsername(any())).thenReturn("username")

        whenever(authService.loadUserByUsername(anyString()))
            .thenReturn(UserAuthDetails(User("username", "password")))

        whenever(jwtService.isValid(any(), any())).thenReturn(false)

        assertThrows<InvalidBearerToken> {
            jwtAuthManager.authenticate(BearerToken("token"))
        }
    }
}