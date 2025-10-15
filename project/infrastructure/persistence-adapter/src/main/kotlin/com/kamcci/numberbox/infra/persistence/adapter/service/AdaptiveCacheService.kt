package com.kamcci.numberbox.infra.persistence.adapter.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class AdaptiveCacheService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val hashBasedReplicationService: HashBasedReplicationService
) {
    // 키별 접근 횟수 카운팅
    private val accessCountMap = ConcurrentHashMap<String, AtomicLong>()

    // 핫 키로 판단된 키들을 저장
    private val hotKeySet = ConcurrentHashMap.newKeySet<String>()

    companion object {
        const val HOT_KEY_THRESHOLD = 1000L // 분당 1000회 이상 접근시 핫 키로 판단
        const val MONITORING_INTERVAL = 60000L // 1분마다 핫 키 업데이트
    }

    /**
     * 캐시 조회 - 핫 키는 자동으로 Cache Smearing 적용
     */
    fun get(key: String): Any? {
        // 접근 기록
        recordAccess(key)

        // 핫 키로 등록되어 있으면 Cache Smearing으로 조회
        return if (hotKeySet.contains(key)) {
            hashBasedReplicationService.getHashBasedHotKey(key)
        } else {
            redisTemplate.opsForValue().get(key)
        }
    }

    /**
     * 캐시 저장 - 핫 키는 자동으로 복제본 생성
     */
    fun set(key: String, value: Any, ttl: Duration) {
        if (hotKeySet.contains(key)) {
            // 핫 키는 여러 복제본으로 분산 저장
            hashBasedReplicationService.setHashBasedHotKey(key, value, ttl)
        } else {
            redisTemplate.opsForValue().set(key, value, ttl)
        }
    }

    /**
     * 키 접근 기록
     */
    private fun recordAccess(key: String) {
        accessCountMap.computeIfAbsent(key) { AtomicLong(0) }
            .incrementAndGet()
    }

    /**
     * 핫 키 감지 로직
     */
    private fun detectHotKeys(): Set<String> {
        return accessCountMap.entries
            .filter { it.value.get() > HOT_KEY_THRESHOLD }
            .map { it.key }
            .toSet()
    }

    /**
     * 주기적으로 핫 키 목록 업데이트 및 카운터 리셋
     */
    @Scheduled(fixedDelay = MONITORING_INTERVAL)
    fun updateHotKeys() {
        val currentHotKeys = detectHotKeys()

        // 새로 감지된 핫 키 추가
        val newHotKeys = currentHotKeys - hotKeySet
        newHotKeys.forEach { key ->
            hotKeySet.add(key)
        }

        // 더 이상 핫 키가 아닌 키 제거
        val coldKeys = hotKeySet - currentHotKeys
        coldKeys.forEach { key ->
            hotKeySet.remove(key)
        }

        // 카운터 리셋 (다음 주기를 위해)
        accessCountMap.clear()
    }
}