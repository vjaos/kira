package com.example.kira

import com.example.kira.configuration.AuthProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    AuthProperties::class
)
class KiraApplication

fun main(args: Array<String>) {
    runApplication<KiraApplication>(*args)
}
