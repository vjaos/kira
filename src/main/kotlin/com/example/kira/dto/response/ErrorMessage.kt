package com.example.kira.dto.response

import com.example.kira.exception.LogicErrorType

data class ErrorMessage(val type: LogicErrorType, val message: String)