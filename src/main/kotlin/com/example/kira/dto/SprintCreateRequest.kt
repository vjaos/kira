package com.example.kira.dto

import java.util.Date

data class SprintCreateRequest(
        val name: String,
        val targets: String?,
        val deadline: Date
)
