package com.kamcci.numberbox.infra.redis.adapter.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import io.lettuce.core.ClientOptions
import io.lettuce.core.ReadFrom
import io.lettuce.core.SocketOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisNode
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
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
@EnableConfigurationProperties(value = [RedisServerProperty::class])
class RedisConfig(
    private val redisServerProperty: RedisServerProperty
) {
    companion object {
        const val REDIS_2WEEK_CACHE_MANAGER_BEAN = "redis2WeekCacheManager"
        const val REDIS_MEMBER_CACHE_MANAGER_BEAN = "redisMemberCacheManager"
    }

    // redis 단일서버 구성
//    @Bean
//    fun redisConnectionFactory(): RedisConnectionFactory {
//        return LettuceConnectionFactory(redisServerProperty.ip, redisServerProperty.port.toInt())
//    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val nodes: List<String> = redisServerProperty.nodes
        val maxRedirects: Int = 3
        val redisNodes = nodes.stream()
            .map { node: String ->
                RedisNode(node.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0],
                    node.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].toInt())
            }
            .toList()

        // (1) Redis Cluster 설정
        val clusterConfiguration = RedisClusterConfiguration()
        clusterConfiguration.setClusterNodes(redisNodes)
        clusterConfiguration.setMaxRedirects(maxRedirects)

        // (2) Socket 옵션
        val socketOptions: SocketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofMillis(100L))
            .keepAlive(true)
            .build()

        // (3) Cluster topology refresh 옵션
        val clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            .dynamicRefreshSources(true)
            .enableAllAdaptiveRefreshTriggers()
            .enablePeriodicRefresh(Duration.ofMinutes(30L))
            .build()

        // (4) Cluster Client 옵션
        val clientOptions: ClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            .socketOptions(socketOptions)
            .build()

        // (5) Lettuce Client 옵션
        val clientConfiguration = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofMillis(3000L))
            .build()
        return LettuceConnectionFactory(clusterConfiguration, clientConfiguration)
    }

    // redis 클러스터 구성
//    @Bean
//    fun redisConnectionFactory(): RedisConnectionFactory {
//        val nodes = redisServerProperty.nodes
//
//        val clusterConfig = RedisClusterConfiguration(nodes).apply {
//            maxRedirects = 3          // 선택
//        }
//
//        val lettuceClientConfig = LettuceClientConfiguration.builder()
//            .readFrom(ReadFrom.REPLICA_PREFERRED) // 읽기는 레플리카 우선
//            .build()
//
//        return LettuceConnectionFactory(clusterConfig, lettuceClientConfig)
//    }

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
            .entryTtl(Duration.ofHours(3L))
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