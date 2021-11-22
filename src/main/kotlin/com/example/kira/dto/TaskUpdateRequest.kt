package com.example.kira.dto

data class TaskUpdateRequest(
    val name: String,
    val description: String?,
    val status: String?,
    val priority: String?
)