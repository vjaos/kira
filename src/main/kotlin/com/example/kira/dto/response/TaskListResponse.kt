package com.example.kira.dto.response

import com.example.kira.dto.TaskInfo

data class TaskListResponse(val totalCount: Int, val taskList: List<TaskInfo>)
