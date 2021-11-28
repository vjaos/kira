package com.example.kira.service

import com.example.kira.dto.TaskPerformers
import com.example.kira.dto.request.TaskUpdateRequest
import com.example.kira.exception.BusinessLogicException
import com.example.kira.exception.LogicErrorType
import com.example.kira.repository.TaskRepository
import com.example.kira.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) {

    fun deleteTaskById(id: Long) {
        if (taskRepository.existsById(id)) taskRepository.deleteById(id)
    }

    fun updateTask(id: Long, request: TaskUpdateRequest) {
        taskRepository.findByIdOrNull(id)
            ?.apply {
                name = request.name ?: this.name
                description = request.description ?: this.description
                status = request.status ?: this.status
                priority = request.priority ?: this.priority
            }
            ?.let { taskRepository.save(it) }
            ?: throw BusinessLogicException(LogicErrorType.TASK_NOT_FOUND, "Task $id not found")
    }

    fun assignTaskToUsers(taskId: Long, taskPerformers: TaskPerformers) {
        val task = taskRepository.findByIdOrNull(taskId)
            ?: throw BusinessLogicException(LogicErrorType.TASK_NOT_FOUND, "Task with id $taskId not found")

        val users = taskPerformers.performers.mapNotNull { userRepository.findByUsername(it) }

        if (users.isEmpty())
            throw BusinessLogicException(LogicErrorType.PERFORMERS_NOT_FOUND, "Cannot find any given performers")

        task.performers.addAll(users)
        taskRepository.save(task)
    }
}
