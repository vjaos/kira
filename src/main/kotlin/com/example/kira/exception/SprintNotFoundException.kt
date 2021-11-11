package com.example.kira.exception

import java.lang.RuntimeException

class SprintNotFoundException(override val message: String) : RuntimeException(message)
