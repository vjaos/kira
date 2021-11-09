package com.example.kira.controller

import com.example.kira.dto.SprintListResponse
import com.example.kira.dto.SprintCreateRequest
import com.example.kira.service.SprintService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\$UrlConstants.API_URL/sprints")
class SprintController(val sprintService: SprintService) {

    @GetMapping
    fun getSprintList(): ResponseEntity<SprintListResponse>? {
        return ResponseEntity<SprintListResponse>(sprintService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun createSprint(@RequestBody sprint: SprintCreateRequest): ResponseEntity<*>? {
        return ResponseEntity<Any>(sprintService.createSprint(sprint), HttpStatus.CREATED)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteSprint(@PathVariable("id") id: Long): ResponseEntity<*>? {
        sprintService.deleteSprintById(id)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }
}