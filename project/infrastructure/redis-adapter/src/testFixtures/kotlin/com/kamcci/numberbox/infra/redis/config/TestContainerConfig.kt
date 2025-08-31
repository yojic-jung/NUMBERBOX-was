package com.kamcci.numberbox.infra.redis.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName


class TestContainerConfig : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        val redisImage = "redis:7.0.8"
        val port = 6379
        val redis = GenericContainer(DockerImageName.parse(redisImage))
            .withExposedPorts(port)

        redis.start()

        System.setProperty("spring.data.redis.host", redis.host)
        System.setProperty("spring.data.redis.port", redis.getMappedPort(port).toString())
    }
}
