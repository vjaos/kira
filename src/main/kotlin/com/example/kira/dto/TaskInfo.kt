package com.example.kira.dto

import com.example.kira.entity.Comment

data class TaskInfo(
    val name: String,
    val description: String,
    val status: String,
    val priority: String,
    val performers: List<String>,
    val comments: Set<Comment>
)