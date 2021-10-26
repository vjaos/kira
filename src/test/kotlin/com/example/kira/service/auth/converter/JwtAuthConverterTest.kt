package com.example.kira.service.auth.converter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpServletRequest

class JwtAuthConverterTest {

    val jwtAuthConverter: JwtAuthConverter = JwtAuthConverter()

    @Test
    fun `when request does not contains bearer token return null`() {
        val request = MockHttpServletRequest().apply { addHeader(HttpHeaders.AUTHORIZATION, "Basic token") }
        assertThat(jwtAuthConverter.convert(request)).isNull()
    }
}