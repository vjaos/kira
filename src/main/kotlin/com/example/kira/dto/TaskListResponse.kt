package com.example.kira.dto

import com.example.kira.entity.Task

data class TaskListResponse(val totalCount: Int = 0, val taskList: List<Task> = emptyList())
