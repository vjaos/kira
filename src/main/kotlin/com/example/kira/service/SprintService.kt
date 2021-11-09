package com.example.kira.service

import com.example.kira.entity.Sprint
import com.example.kira.dto.SprintCreateRequest
import com.example.kira.dto.SprintListResponse
import com.example.kira.repository.SprintRepository
import org.springframework.stereotype.Service

@Service
class SprintService(private val sprintRepository: SprintRepository) {
    fun getAll(): SprintListResponse {
        val sprints = sprintRepository.findAll()
        return SprintListResponse(sprints.count(), sprints.toList())
    }

    fun createSprint(request: SprintCreateRequest): Sprint {
        return sprintRepository.save(
                Sprint(
                        name = request.name,
                        targets = request.targets,
                        deadline = request.deadline
                )
        )
    }

    fun deleteSprintById(id: Long) {
        sprintRepository.deleteById(id)
    }
}