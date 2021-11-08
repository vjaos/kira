package com.example.kira.controller

import com.example.kira.entity.Task
import com.example.kira.config.UrlConstants
import com.example.kira.dto.TaskListResponse
import com.example.kira.dto.TaskCreateRequest
import com.example.kira.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(UrlConstants.API_URL + "task")
class TaskController(val taskService: TaskService) {

    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCarList(): ResponseEntity<*>? {
        val response = TaskListResponse()
        val tasks: List<Task> = taskService.getAll()
        response.totalCount = tasks.size
        response.taskList = tasks
        return ResponseEntity<Any>(response, HttpStatus.OK)
    }

    @PostMapping(value = [""])
    fun createTask(@RequestBody task: TaskCreateRequest): ResponseEntity<*>? {
        return ResponseEntity<Any>(taskService.createTask(task), HttpStatus.CREATED)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteTask(@PathVariable("id") id: Long): ResponseEntity<*>? {
        taskService.deleteTaskById(id)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }
}
