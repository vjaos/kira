package com.example.kira.controller

import com.example.kira.config.UrlConstants
import com.example.kira.dto.TaskListResponse
import com.example.kira.dto.TaskCreateRequest
import com.example.kira.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${UrlConstants.API_URL}/tasks")
class TaskController(val taskService: TaskService) {

    @GetMapping
    fun getTaskList(): ResponseEntity<TaskListResponse> {
        return ResponseEntity<TaskListResponse>(taskService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun createTask(@RequestBody task: TaskCreateRequest): ResponseEntity<HttpStatus> {
        taskService.createTask(task)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> {
        taskService.deleteTaskById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }
}
