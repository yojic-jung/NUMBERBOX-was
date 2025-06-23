package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer

@EnableConfigurationProperties(value = [TestRedisProperty::class])
@TestConfiguration
class TestRedisConfig(
    @Autowired
    testRedisProperty: TestRedisProperty
) {
    private val redisServer: RedisServer = RedisServer(testRedisProperty.port)

    @PostConstruct
    fun postConstruct() {
        redisServer.start()
    }

    @PreDestroy
    fun preDestory() {
        redisServer.stop()
    }
}
