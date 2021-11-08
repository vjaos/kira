package com.example.kira.dto

import com.example.kira.entity.Task

data class TaskListResponse(var totalCount: Int = 0, var taskList: List<Task> = emptyList())
