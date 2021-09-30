package com.example.kira

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KiraApplication

fun main(args: Array<String>) {
    runApplication<KiraApplication>(*args)
}
