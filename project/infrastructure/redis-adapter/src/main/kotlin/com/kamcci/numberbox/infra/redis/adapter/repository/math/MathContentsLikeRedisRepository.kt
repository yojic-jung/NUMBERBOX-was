package com.kamcci.numberbox.infra.redis.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessSeverException
import com.kamcci.numberbox.infra.redis.adapter.common.RedisKeyGenerator
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.TimeUnit

@Repository
class MathContentsLikeRedisRepository(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    companion object {
        // 예외 메시지
        const val LIKE_SAVE_FAIL = "좋아요 정보 저장되지 않음"
        const val LIKE_DELETE_FAIL = "좋아요 취소되지 않음"

        // 캐싱 시간
        const val LIKE_TTL = 12L
    }

    fun hasLikeKey(contentsId: Long): Boolean {
        return stringRedisTemplate.hasKey(RedisKeyGenerator.getMathLikeKey(contentsId))
    }


    fun cacheLikeMember(contentsId: Long, memberIds: List<UUID>) {
        val key = RedisKeyGenerator.getMathLikeKey(contentsId)
        val setOps = stringRedisTemplate.opsForSet()

        // UUID 리스트를 문자열로 변환해서 Redis Set에 추가
        if (memberIds.isNotEmpty()) {
            // UUID 리스트를 문자열로 변환해서 Redis Set에 추가
            val stringIds = memberIds.map { it.toString() }.toTypedArray()
            setOps.add(key, *stringIds)
        }

        // TTL 설정 (예: 3시간)
        stringRedisTemplate.expire(key, LIKE_TTL, TimeUnit.HOURS)
    }


    fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        val setOps = stringRedisTemplate.opsForSet()
        val key = RedisKeyGenerator.getMathLikeKey(modifyDto.contentsId)
        val addedCount = setOps.add(key, modifyDto.memberId.toString())
            ?: throw BusinessSeverException(LIKE_SAVE_FAIL)
        if (addedCount > 0) {
            // 새로 추가된 좋아요면 TTL 설정 (TTL 없으면 만료 안 됨)
            stringRedisTemplate.expire(key, LIKE_TTL, TimeUnit.HOURS)
        }
        return true
    }

    fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        val setOps = stringRedisTemplate.opsForSet()
        return setOps.remove(RedisKeyGenerator.getMathLikeKey(modifyDto.contentsId), modifyDto.memberId.toString())
            ?: throw BusinessSeverException(LIKE_DELETE_FAIL)
    }

    fun existBy(contentsId: Long, memberId: UUID): Boolean {
        val key = RedisKeyGenerator.getMathLikeKey(contentsId)
        return stringRedisTemplate.opsForSet().isMember(key, memberId.toString()) ?: false
    }

    fun countBy(contentsId: Long): Long {
        val key = RedisKeyGenerator.getMathLikeKey(contentsId)
        return stringRedisTemplate.opsForSet().size(key) ?: 0L
    }
}