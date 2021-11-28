package com.example.kira.controller

import com.example.kira.dto.response.Jwt
import com.example.kira.dto.Login
import com.example.kira.dto.request.UserCreateRequest
import com.example.kira.service.auth.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody login: Login): Jwt = authService.login(login)

    @PostMapping("/register")
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest) {
        authService.register(userCreateRequest)
    }
}
