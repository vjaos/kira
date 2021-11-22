package com.example.kira.dto

data class TaskCreateRequest(
        val name: String,
        val description: String,
        val status: String,
        val priority: String
)
