package com.example.kira.exception

import java.lang.RuntimeException

class UserAlreadyExistsException(override val message: String) : RuntimeException(message)