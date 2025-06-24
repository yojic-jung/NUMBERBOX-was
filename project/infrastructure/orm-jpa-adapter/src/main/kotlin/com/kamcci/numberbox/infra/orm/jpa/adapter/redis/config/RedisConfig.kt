package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableRedisRepositories
class RedisConfig {
    companion object {
        const val REDIS_2WEEK_CACHE_MANAGER_BEAN = "redis2WeekCacheManager"
        const val REDIS_MEMBER_CACHE_MANAGER_BEAN = "redisMemberCacheManager"
    }

    private val host: String = "localhost"
    private val port = 6379

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    @Primary
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(defaultCacheConfiguration())
            .build()
    }

    @Bean(name = [REDIS_2WEEK_CACHE_MANAGER_BEAN])
    fun redis2WeekCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(ttl2WeekacheConfiguration())
            .build()
    }

    private fun ttl2WeekacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofDays(14L))
            .disableCachingNullValues()
    }

    private fun defaultCacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofHours(1L))
            .disableCachingNullValues()
    }

    @Bean(name = [REDIS_MEMBER_CACHE_MANAGER_BEAN])
    fun redisMemberCacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(memberCacheConfiguration())
            .build()
    }

    private fun memberCacheConfiguration(): RedisCacheConfiguration {
        val kotlinModule = KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .configure(KotlinFeature.SingletonSupport, true)
            .configure(KotlinFeature.StrictNullChecks, true)
            .build()

        val objectMapper = ObjectMapper()
            .registerModule(kotlinModule)
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

        val serializer = Jackson2JsonRedisSerializer(objectMapper, MemberRedisHash::class.java)

        return RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofHours(1L))
            .disableCachingNullValues()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
    }

    @Bean
    fun longRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Long> {
        val redisTemplate: RedisTemplate<String, Long> = RedisTemplate()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.hashValueSerializer = GenericToStringSerializer(Long::class.java)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericToStringSerializer(Long::class.java)
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}