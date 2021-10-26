package com.example.kira.advice

import com.example.kira.dto.ErrorMessage
import com.example.kira.exception.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AuthControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExits(
        exception: UserAlreadyExistsException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessage(exception.message))
}