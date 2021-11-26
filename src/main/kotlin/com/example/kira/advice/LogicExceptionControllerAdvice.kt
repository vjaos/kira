package com.example.kira.advice

import com.example.kira.dto.ErrorMessage
import com.example.kira.exception.BusinessLogicException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class LogicExceptionControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BusinessLogicException::class)
    fun handleLoginException(
        exception: BusinessLogicException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage> =
        ResponseEntity.status(exception.type.status).body(ErrorMessage(exception.type, exception.message))
}