package com.example.kira.dto

import com.example.kira.entity.Sprint

data class SprintListResponse(val totalCount: Int = 0, val sprintList: List<Sprint> = emptyList())
