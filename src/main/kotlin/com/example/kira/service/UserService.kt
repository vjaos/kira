package com.example.kira.service

import com.example.kira.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository)
