package com.example.kira.service

import com.example.kira.entity.Task
import com.example.kira.dto.TaskCreateRequest
import com.example.kira.dto.TaskListResponse
import com.example.kira.exception.TaskNotFoundException
import com.example.kira.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun getAll(): TaskListResponse =
            taskRepository.findAll().let { TaskListResponse(it.count(), it.toList()) }

    fun createTask(request: TaskCreateRequest): Task {
        return taskRepository.save(
                Task(
                        name = request.name,
                        description = request.description,
                        status = request.status,
                        priority = request.priority
                )
        )
    }

    fun deleteTaskById(id: Long) {
        if (taskRepository.existsById(id)) taskRepository.deleteById(id)
    }
}
