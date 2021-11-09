package com.example.kira

import com.example.kira.dto.Jwt
import com.example.kira.dto.Login
import com.example.kira.repository.UserRepository
import com.example.kira.repository.TaskRepository
import com.example.kira.repository.SprintRepository
import com.example.kira.util.PostgresInitializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [PostgresInitializer::class])
class WebIntegrationTest {

    @Autowired
    lateinit var context: WebApplicationContext
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var taskRepository: TaskRepository
    @Autowired
    lateinit var sprintRepository: SprintRepository
    lateinit var mockMvc: MockMvc

    val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }


    fun getAuthToken(login: Login): Jwt {
        val result = mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(login)
        }.andReturn()

        return mapper.readValue(result.response.contentAsString, Jwt::class.java)
    }
}