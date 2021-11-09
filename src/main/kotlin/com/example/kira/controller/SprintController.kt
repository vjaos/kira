package com.example.kira.controller

import com.example.kira.config.UrlConstants
import com.example.kira.dto.SprintListResponse
import com.example.kira.dto.SprintCreateRequest
import com.example.kira.service.SprintService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${UrlConstants.API_URL}/sprints")
class SprintController(val sprintService: SprintService) {

    @GetMapping
    fun getSprintList(): ResponseEntity<SprintListResponse> {
        return ResponseEntity<SprintListResponse>(sprintService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun createSprint(@RequestBody sprint: SprintCreateRequest): ResponseEntity<HttpStatus> {
        sprintService.createSprint(sprint)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteSprint(@PathVariable("id") id: Long): ResponseEntity<HttpStatus> {
        sprintService.deleteSprintById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }
}