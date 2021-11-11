package com.example.kira.controller

import com.example.kira.WebIntegrationTest
import com.example.kira.config.UrlConstants
import com.example.kira.dto.Login
import com.example.kira.entity.User
import com.example.kira.entity.Sprint
import org.apache.http.HttpHeaders
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.util.*

class SprintControllerTest : WebIntegrationTest() {
    lateinit var token: String
    val date = LocalDate.of(2021, 1, 1)

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
    fun `should return sprint list`() {
        sprintRepository.save(
                Sprint(
                        name = "name",
                        deadline = date
                )
        )
        val expectedJson = """
        {
            "totalCount": 1,
            "sprintList": [
                {
                    "name": "name",
                    "deadline": "$date",
                    "targets": "",
                    "tasks": []
                }
            ]
        }
        """
        mockMvc.get("${UrlConstants.API_URL}/sprints") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isOk() }
                    content { json(expectedJson) }
                }
    }

    @Test
    fun `should create sprint`() {
        mockMvc.post("${UrlConstants.API_URL}/sprints") {
            header(HttpHeaders.AUTHORIZATION, token)
            contentType = MediaType.APPLICATION_JSON
            content = """ { "name": "name", "deadline": "$date" } """
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `when incorrect data was given should return bad request`() {
        mockMvc.post("${UrlConstants.API_URL}/sprints") {
            header(HttpHeaders.AUTHORIZATION, token)
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `should delete sprint`() {
        val id = sprintRepository.save(Sprint(name = "name", deadline = LocalDate.of(2021, 1, 1))).id
        mockMvc.delete("${UrlConstants.API_URL}/sprints/$id") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isNoContent() }
                }
    }

    @Test
    fun `when delete sprint with wrong id should return no content`() {
        mockMvc.delete("${UrlConstants.API_URL}/sprints/0") { header(HttpHeaders.AUTHORIZATION, token) }
                .andExpect {
                    status { isNoContent() }
                }
    }

    @AfterEach
    fun deleteInstances() {
        userRepository.deleteAll()
        sprintRepository.deleteAll()
    }
}
