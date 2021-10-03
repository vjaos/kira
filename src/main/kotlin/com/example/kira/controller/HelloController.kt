package com.example.kira.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
class HelloController {


    @GetMapping("/hello")
    fun sayHello(@AuthenticationPrincipal principal: Principal) = Message("Hello, ${principal.name}")
}


data class Message(val text: String)