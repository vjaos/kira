package com.example.kira.controller

import com.example.kira.config.UrlConstants
import com.example.kira.dto.request.TaskCreateRequest
import com.example.kira.dto.response.TaskListResponse
import com.example.kira.dto.TaskPerformers
import com.example.kira.dto.request.TaskUpdateRequest
import com.example.kira.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${UrlConstants.API_URL}/tasks")
class TaskController(val taskService: TaskService) {

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> {
        taskService.deleteTaskById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable("id") taskId: Long, @RequestBody request: TaskUpdateRequest): ResponseEntity<HttpStatus> {
        taskService.updateTask(taskId, request)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}/performers")
    fun assignToUser(@PathVariable("id") taskId: Long, @RequestBody taskPerformers: TaskPerformers): ResponseEntity<HttpStatus> {
        taskService.assignTaskToUsers(taskId, taskPerformers)
        return ResponseEntity.ok().build()
    }
}
