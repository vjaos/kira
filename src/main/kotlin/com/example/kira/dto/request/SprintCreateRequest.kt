package com.example.kira.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class SprintCreateRequest(
        val name: String,
        val targets: String?,
        @DateTimeFormat(pattern = "yyyy.MM.dd")
        val deadline: LocalDate
)
