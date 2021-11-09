package com.example.kira.controller

import com.example.kira.WebIntegrationTest
import com.example.kira.config.UrlConstants
import com.example.kira.dto.Login
import com.example.kira.entity.Task
import com.example.kira.entity.User
import org.apache.http.HttpHeaders
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class TaskControllerTest : WebIntegrationTest() {
    lateinit var token: String

    @BeforeEach
    fun initAuth() {
        userRepository.save(
                User(
                        "test",
                        "\$2a\$12\$ZCcu.RZDaIDmw//9m/3OjeNmLgqqFu94Kk55dG/cGSdxl8qS5H.kC"
                )
        )
        token = "Bearer " + getAuthToken(Login("test", "password")).token
    }


    @Test
    fun `should return task list`() {
        taskRepository.save(
                Task(
                        name = "name",
                        description = "description",
                        status = "status",
                        priority = "priority"
                )
        )
        val expectedJson = """
        {
            "totalCount": 1, 
            "taskList": [
                { 
                    "name": "name", 
                    "description": "description", 
                    "status": "status", 
                    "priority": "priority", 
                    "comments": [], 
                    "performers": [] 
                }
            ]
        }
        """
        mockMvc.get("${UrlConstants.API_URL}/tasks") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isOk() }
                    content { json(expectedJson) }
                }
    }

    @Test
    fun `should create task`() {
        mockMvc.post("${UrlConstants.API_URL}/tasks") {
            header(HttpHeaders.AUTHORIZATION, token)
            contentType = MediaType.APPLICATION_JSON
            content = """ { "name": "name" } """
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `when incorrect data was given should return bad request`() {
        mockMvc.post("${UrlConstants.API_URL}/tasks") {
            header(HttpHeaders.AUTHORIZATION, token)
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `should delete task`() {
        val id = taskRepository.save(Task(name = "name")).id
        mockMvc.delete("${UrlConstants.API_URL}/tasks/$id") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isNoContent() }
                }
    }

    @Test
    fun `when delete task with wrong id should return no content`() {
        mockMvc.delete("${UrlConstants.API_URL}/tasks/0") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isNoContent() }
                }
    }

    @AfterEach
    fun deleteInstances() {
        userRepository.deleteAll()
        taskRepository.deleteAll()
    }
}