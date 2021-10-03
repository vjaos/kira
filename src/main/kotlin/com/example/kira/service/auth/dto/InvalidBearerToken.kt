package com.example.kira.service.auth.dto

import org.springframework.security.core.AuthenticationException

class InvalidBearerToken(message: String?): AuthenticationException(message)