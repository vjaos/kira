package com.example.kira.service

import com.example.kira.dto.UserCreateRequest
import com.example.kira.entity.User
import com.example.kira.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
}
