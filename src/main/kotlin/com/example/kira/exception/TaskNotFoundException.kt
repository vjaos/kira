package com.example.kira.exception

import java.lang.RuntimeException

class TaskNotFoundException(override val message: String) : RuntimeException(message)
