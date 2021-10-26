package com.example.kira.controller

import com.example.kira.WebIntegrationTest
import com.example.kira.dto.Login
import com.example.kira.entity.User
import org.apache.http.HttpHeaders
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get

class HelloControllerTest: WebIntegrationTest() {

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

    @AfterEach
    fun clearDb() {
        userRepository.deleteAll()
    }

    @Test
    fun `send request to secured endpoint`() {
        mockMvc.get("/api/hello") { header(HttpHeaders.AUTHORIZATION, token) }
            .andExpect {
                status { isOk() }
                content { json("""{ "message": "Hello, test" }""") }
            }
    }
}