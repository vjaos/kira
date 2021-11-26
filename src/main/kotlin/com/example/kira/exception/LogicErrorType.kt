package com.example.kira.exception

import org.springframework.http.HttpStatus

enum class LogicErrorType(val status: HttpStatus) {
    PERFORMERS_NOT_FOUND(HttpStatus.NOT_FOUND),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND)
}