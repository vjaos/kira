package com.example.kira.dto

import com.example.kira.entity.Comment
import com.example.kira.entity.TaskStatus

data class TaskInfo(
    val name: String,
    val description: String,
    val status: TaskStatus,
    val priority: String,
    val performers: List<String>,
    val comments: Set<Comment>
)