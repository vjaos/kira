package com.example.kira

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [PostgresInitializer::class])
class KiraApplicationTests {

    @Test
    fun contextLoads() {
    }
}
