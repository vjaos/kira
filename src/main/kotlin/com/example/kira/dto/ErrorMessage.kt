package com.example.kira.dto

import com.example.kira.exception.LogicErrorType

data class ErrorMessage(val type: LogicErrorType, val message: String)