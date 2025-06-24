package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessSeverException
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.RedisKeyGenerator.getMathLikeKey
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Primary
@Repository
class MathContentsLikeWriteRedisRepository(
    private val stringRedisTemplate: StringRedisTemplate
) : MathContentsLikeWriteOrmPort {
    companion object {
        // 예외 메시지
        const val LIKE_SAVE_FAIL = "좋아요 정보 저장되지 않음"
        const val LIKE_DELETE_FAIL = "좋아요 취소되지 않음"
    }

    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        val setOps = stringRedisTemplate.opsForSet()
        val key = getMathLikeKey(modifyDto.contentsId)
        val addedCount = setOps.add(key, modifyDto.memberId.toString())
            ?: throw BusinessSeverException(LIKE_SAVE_FAIL)
        if (addedCount > 0) {
            // 새로 추가된 좋아요면 TTL 설정 (TTL 없으면 만료 안 됨)
            stringRedisTemplate.expire(key, 30, TimeUnit.DAYS)
        }
        return true
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        val setOps = stringRedisTemplate.opsForSet()
        setOps.remove(getMathLikeKey(modifyDto.contentsId), modifyDto.memberId.toString())
            ?: throw BusinessSeverException(LIKE_DELETE_FAIL)
        return 1L
    }

}