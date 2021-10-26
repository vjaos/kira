package com.example.kira.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/api/hello")
    fun hello(@AuthenticationPrincipal username: String): ResponseEntity<String> =
        ResponseEntity.ok("""{ "message": "Hello, $username" }""")
}