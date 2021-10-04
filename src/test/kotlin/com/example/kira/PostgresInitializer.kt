package com.example.kira

import org.springframework.boot.test.util.TestPropertyValues.of
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait


class PostgresInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val postgresContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE)
        .apply {
            waitingFor(Wait.defaultWaitStrategy())
            withExposedPorts(POSTGRES_PORT)
            withUsername(POSTGRES_USER_VAL)
            withPassword(POSTGRES_PASSWORD_VAL)
        }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val postgresImage = applicationContext.environment.getProperty(POSTGRES_IMAGE_ENV)

        if (!postgresImage.isNullOrEmpty()) {
            postgresContainer.dockerImageName = postgresImage
        }

        postgresContainer.start()

        val mappedPort = postgresContainer.getMappedPort(POSTGRES_PORT)

        of(
            "spring.datasource.url=jdbc:postgresql://${postgresContainer.containerIpAddress}:$mappedPort/test",
            "spring.datasource.username=$POSTGRES_USER_VAL",
            "spring.datasource.password=$POSTGRES_PASSWORD_VAL"
        ).applyTo(applicationContext.environment)
    }


    companion object {
        const val POSTGRES_PORT = 5432
        const val POSTGRES_IMAGE = "postgres"
        const val POSTGRES_USER_VAL = "test_user"
        const val POSTGRES_PASSWORD_VAL = "test123"
        const val POSTGRES_IMAGE_ENV = "INITIALIZER_POSTGRES_IMAGE"
    }
}