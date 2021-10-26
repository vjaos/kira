package com.example.kira.controller


import com.example.kira.WebIntegrationTest
import com.example.kira.entity.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post


class AuthControllerTest : WebIntegrationTest() {

    @BeforeEach
    fun createUser() {
        userRepository.save(
            User(
                "user",
                "\$2a\$12\$ZCcu.RZDaIDmw//9m/3OjeNmLgqqFu94Kk55dG/cGSdxl8qS5H.kC"
            )
        )
    }

    @Test
    fun `successfully create new user`() {
        mockMvc.post("/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """ { "username": "login", "password": "password" } """
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `when user already exists send error message`() {
        mockMvc.post("/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """ { "username": "user", "password": "password" } """
        }.andExpect {
            status { isUnauthorized() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("""{ "message": "User 'user' already exists" }""") }
        }
    }

    @Test
    fun `successfully login user`() {
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """ { "username": "user", "password": "password" } """
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.token") { isNotEmpty() }
        }
    }

    @AfterEach
    fun deleteUser() {
        userRepository.deleteAll()
    }
}