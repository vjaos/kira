package com.example.kira.service

import com.example.kira.dto.ErrorMessage
import com.example.kira.dto.TaskCreateRequest
import com.example.kira.dto.TaskInfo
import com.example.kira.dto.TaskListResponse
import com.example.kira.dto.TaskPerformers
import com.example.kira.dto.TaskUpdateRequest
import com.example.kira.entity.Task
import com.example.kira.entity.User
import com.example.kira.exception.BusinessLogicException
import com.example.kira.exception.LogicErrorType
import com.example.kira.repository.TaskRepository
import com.example.kira.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import kotlin.math.E

@Service
@Transactional
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) {

    fun getAll(): TaskListResponse =
        taskRepository.findAll()
            .map {
                TaskInfo(
                    name = it.name,
                    description = it.description,
                    priority = it.priority,
                    status = it.status,
                    performers = it.performers.map(User::username),
                    comments = it.comments
                )
            }
            .let { TaskListResponse(it.count(), it.toList()) }

    fun createTask(request: TaskCreateRequest): Task {
        return taskRepository.save(
            Task(
                name = request.name,
                description = request.description ?: EMPTY_STRING,
                status = request.status ?: EMPTY_STRING,
                priority = request.priority ?: EMPTY_STRING
            )
        )
    }

    fun deleteTaskById(id: Long) {
        if (taskRepository.existsById(id)) taskRepository.deleteById(id)
    }

    fun updateTask(id: Long, request: TaskUpdateRequest) {
        taskRepository.findByIdOrNull(id)
            ?.apply {
                name = request.name ?: this.name
                description = request.description ?: this.description
                status = request.status ?: this.status
                priority = request.priority ?: this.status
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


    companion object {
        const val EMPTY_STRING = ""
    }
}
