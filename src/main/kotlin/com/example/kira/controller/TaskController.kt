package com.example.kira.controller

import com.example.kira.dto.TaskListResponse
import com.example.kira.dto.TaskCreateRequest
import com.example.kira.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\$UrlConstants.API_URL/tasks")
class TaskController(val taskService: TaskService) {

    @GetMapping
    fun getTaskList(): ResponseEntity<TaskListResponse>? {
        return ResponseEntity<TaskListResponse>(taskService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun createTask(@RequestBody task: TaskCreateRequest): ResponseEntity<*>? {
        return ResponseEntity<Any>(taskService.createTask(task), HttpStatus.CREATED)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteTask(@PathVariable("id") id: Long): ResponseEntity<*>? {
        taskService.deleteTaskById(id)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }
}
