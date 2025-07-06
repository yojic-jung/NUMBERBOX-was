package com.kamcci.numberbox.infra.redis.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@TestConfiguration
class TestRedisConfig {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val host = System.getProperty("spring.data.redis.host", "localhost")
        val port = System.getProperty("spring.data.redis.port", "6379").toInt()
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }
}