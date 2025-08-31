package com.kamcci.numberbox.infra.redis.adapter.config

import org.junit.jupiter.api.Test


class RedisConfigTest {
    private val redisConfig = RedisConfig(RedisServerProperty(listOf()))

    @Test
    fun `빈 초기화`() {
        redisConfig.redisConnectionFactory()
        redisConfig.redisCacheManager(redisConfig.redisConnectionFactory())
        redisConfig.redis2WeekCacheManager(redisConfig.redisConnectionFactory())
        redisConfig.redisMemberCacheManager(redisConfig.redisConnectionFactory())
        redisConfig.longRedisTemplate(redisConfig.redisConnectionFactory())
    }
}