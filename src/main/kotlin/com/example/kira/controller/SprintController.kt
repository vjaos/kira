package com.example.kira.controller

import com.example.kira.config.UrlConstants
import com.example.kira.dto.request.SprintCreateRequest
import com.example.kira.dto.request.TaskCreateRequest
import com.example.kira.dto.response.SprintListResponse
import com.example.kira.dto.response.TaskListResponse
import com.example.kira.service.SprintService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${UrlConstants.API_URL}/sprints")
class SprintController(val sprintService: SprintService) {

    @GetMapping
    fun getSprintList(): ResponseEntity<SprintListResponse> {
        return ResponseEntity<SprintListResponse>(sprintService.getAll(), HttpStatus.OK)
    }

    @GetMapping("/{id}/tasks")
    fun getSprintTasks(@PathVariable("id") id: Long): ResponseEntity<TaskListResponse> =
        ResponseEntity<TaskListResponse>(sprintService.getSprintTasks(id), HttpStatus.OK)

    @PostMapping("/{id}/tasks")
    fun createTask(
        @PathVariable("id") sprintId: Long,
        @RequestBody request: TaskCreateRequest
    ): ResponseEntity<HttpStatus> {
        sprintService.createTask(sprintId, request)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @PostMapping
    fun createSprint(@RequestBody request: SprintCreateRequest): ResponseEntity<HttpStatus> {
        sprintService.createSprint(request)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteSprint(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> {
        sprintService.deleteSprintById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }
}