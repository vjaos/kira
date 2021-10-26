package com.example.kira.service.auth

import com.example.kira.configuration.AuthProperties
import com.example.kira.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [JwtService::class])
@EnableConfigurationProperties(AuthProperties::class)
class JwtServiceTest {


    @Autowired
    lateinit var jwtService: JwtService


    @Test
    fun `when give expired token return false`() {
        val actual = jwtService.isValid(
            BearerToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM1MjQ0NjM3LCJleHAiOjE2MzUyNDQ2OTd9.c1f71-2tR1bF9jKTdybaV2q7-ibBBfP0KaM_1HoWLoBqZUUfRvy9tZUW4L2kKFDA_uSvAZBeULoD5j6FnJNi7A"),
            UserAuthDetails(User("test", "password"))
        )

        assertThat(actual).isFalse
    }


    @Test
    fun `when user details is null return false`() {
        val actual = jwtService.isValid(
            BearerToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjM1MjQ1MzMyLCJleHAiOjQ3ODg4NDUzMzJ9.BkmQ_dTYtJudLrz-gQg1e7DluVFfRzteaZQjYvqXCslLSPVTjsacN6lDVpQLFI02p8bMr5WtZScjTKvfTpHZxA"),
            null
        )

        assertThat(actual).isFalse
    }

}