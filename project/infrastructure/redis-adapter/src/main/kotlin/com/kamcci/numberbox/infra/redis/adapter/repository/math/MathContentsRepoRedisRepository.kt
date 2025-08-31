package com.kamcci.numberbox.infra.redis.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessServerException
import com.kamcci.numberbox.infra.redis.adapter.common.RedisKeyGenerator
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class MathContentsRepoRedisRepository(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    companion object {
        // 예외 메시지
        const val REPO_SAVE_FAIL = "저장소 정보 저장되지 않음"
        const val REPO_DELETE_FAIL = "저장소 취소되지 않음"
    }


    fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        val setOps = stringRedisTemplate.opsForSet()
        val key = RedisKeyGenerator.getMathRepoKey(modifyDto.contentsId)
        val addedCount = setOps.add(key, modifyDto.memberId.toString())
            ?: throw BusinessServerException(REPO_SAVE_FAIL)
        if (addedCount > 0) {
            // 새로 추가된 저장소면 TTL 설정 (TTL 없으면 만료 안 됨)
            stringRedisTemplate.expire(key, 3, TimeUnit.HOURS)
        }
        return true
    }

    fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        val setOps = stringRedisTemplate.opsForSet()
        return setOps.remove(RedisKeyGenerator.getMathRepoKey(modifyDto.contentsId), modifyDto.memberId.toString())
            ?: throw BusinessServerException(REPO_DELETE_FAIL)
    }
}