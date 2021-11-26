package com.example.kira.exception

import java.lang.RuntimeException

data class BusinessLogicException(val type: LogicErrorType, override val message: String) : RuntimeException(message)