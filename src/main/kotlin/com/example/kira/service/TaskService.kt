package com.example.kira.service

import com.example.kira.dto.TaskCreateRequest
import com.example.kira.dto.TaskListResponse
import com.example.kira.dto.TaskPerformers
import com.example.kira.dto.TaskUpdateRequest
import com.example.kira.entity.Task
import com.example.kira.exception.BusinessLogicException
import com.example.kira.exception.LogicErrorType
import com.example.kira.repository.TaskRepository
import com.example.kira.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) {

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

    @Transactional
    fun deleteTaskById(id: Long) {
        if (taskRepository.existsById(id)) taskRepository.deleteById(id)
    }

    @Transactional
    fun updateTask(id: Long, request: TaskUpdateRequest) {
        taskRepository.findByIdOrNull(id)
            ?.apply {
                name = request.name
                description = request.description
                status = request.status
                priority = request.priority
            }
            ?.let { taskRepository.save(it) }
            ?: throw BusinessLogicException(LogicErrorType.TASK_NOT_FOUND, "Task $id not found")
    }

    @Transactional
    fun assignTaskToUsers(taskId: Long, taskPerformers: TaskPerformers) {
        val task = taskRepository.findByIdOrNull(taskId)
            ?: throw BusinessLogicException(LogicErrorType.TASK_NOT_FOUND, "Task $taskId not found")

        val users = taskPerformers.performers.mapNotNull { userRepository.findByUsername(it) }

        if (users.isEmpty())
            throw BusinessLogicException(LogicErrorType.PERFORMERS_NOT_FOUND, "Cannot find any given performers")

        task.performers.addAll(users)
        taskRepository.save(task)
    }
}
