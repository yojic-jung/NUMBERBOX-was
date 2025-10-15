package com.kamcci.numberbox.infra.persistence.adapter.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Duration

@Service
class HashBasedReplicationService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    companion object {
        const val DEFAULT_REPLICA_COUNT = 5
        const val HASH_REPLICA_PREFIX = "r"
    }

    /**
     * 해시 기반 복제본 키 생성
     */
    private fun generateReplicaKeys(key: String, replicaCount: Int = DEFAULT_REPLICA_COUNT): List<String> {
        return (0 until replicaCount).map { index ->
            val hashSuffix = MessageDigest.getInstance("MD5")
                .digest("$key:$index".toByteArray())
                .joinToString("") { "%02x".format(it) }
                .take(8)
            "$key:$HASH_REPLICA_PREFIX:$hashSuffix"
        }
    }

    /**
     * 모든 복제본에 동일한 값 저장
     */
    fun setHashBasedHotKey(
        key: String,
        value: Any,
        ttl: Duration,
        replicaCount: Int = DEFAULT_REPLICA_COUNT
    ): List<String> {
        val replicaKeys = generateReplicaKeys(key, replicaCount)

        return redisTemplate.execute { connection ->
            connection.openPipeline()

            replicaKeys.forEach { replicaKey ->
                redisTemplate.opsForValue().set(replicaKey, value, ttl)
            }

            connection.closePipeline()
            replicaKeys
        }.orEmpty()
    }

    fun getHashBasedHotKey(key: String, replicaCount: Int = DEFAULT_REPLICA_COUNT): Any? {
        val replicaKeys = generateReplicaKeys(key, replicaCount)

        val selectedKey = replicaKeys.random()

        return redisTemplate.opsForValue().get(selectedKey)
    }
}