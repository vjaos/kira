package com.example.kira.dto.request

import com.example.kira.entity.TaskStatus

data class TaskUpdateRequest(
    val name: String?,
    val description: String?,
    val status: TaskStatus?,
    val priority: String?
)