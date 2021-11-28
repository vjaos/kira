package com.example.kira.service

import com.example.kira.dto.TaskInfo
import com.example.kira.dto.request.SprintCreateRequest
import com.example.kira.dto.request.TaskCreateRequest
import com.example.kira.dto.response.SprintListResponse
import com.example.kira.dto.response.TaskListResponse
import com.example.kira.entity.Sprint
import com.example.kira.entity.Task
import com.example.kira.entity.TaskStatus
import com.example.kira.entity.User
import com.example.kira.exception.BusinessLogicException
import com.example.kira.exception.LogicErrorType
import com.example.kira.repository.SprintRepository
import com.example.kira.repository.TaskRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SprintService(
    private val sprintRepository: SprintRepository,
    private val taskRepository: TaskRepository
) {

    fun getAll(): SprintListResponse =
        sprintRepository.findAll().let { SprintListResponse(it.count(), it.toList()) }

    fun createSprint(request: SprintCreateRequest): Sprint =
         sprintRepository.save(
            Sprint(
                name = request.name,
                targets = request.targets,
                deadline = request.deadline
            )
        )


    fun createTask(sprintId: Long, request: TaskCreateRequest) {
        val sprint = sprintRepository.findByIdOrNull(sprintId)
            ?: throw BusinessLogicException(LogicErrorType.SPRINT_NOT_FOUND, "Sprint with $sprintId not found")

        val task = taskRepository.save(
            Task(
                name = request.name,
                description = request.description ?: EMPTY_STRING,
                status = request.status ?: TaskStatus.TODO,
                priority = request.priority ?: EMPTY_STRING
            )
        )

        sprint.tasks.add(task)
        sprintRepository.save(sprint)
    }

    fun getSprintTasks(sprintId: Long): TaskListResponse {
        val sprint = sprintRepository.findByIdOrNull(sprintId)
            ?: throw BusinessLogicException(LogicErrorType.SPRINT_NOT_FOUND, "Sprint with $sprintId not found")

        return sprint.tasks
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
    }


    fun deleteSprintById(id: Long) {
        if (sprintRepository.existsById(id)) sprintRepository.deleteById(id)
    }

    companion object {
        const val EMPTY_STRING = ""
    }
}
