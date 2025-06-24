package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessSeverException
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.RedisKeyGenerator
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit


@Primary
@Repository
class MathContentsRepoWriteRedisRepository(
    private val stringRedisTemplate: StringRedisTemplate
) : MathContentsRepoWriteOrmPort {
    companion object {
        const val REPO_SAVE_FAIL = "저장소 저장에 실패"
        const val REPO_DELETE_FAIL = "저장소 삭제 취소에 실패"
    }

    override fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        val setOps = stringRedisTemplate.opsForSet()
        val key = RedisKeyGenerator.getMathRepoKey(modifyDto.contentsId)
        val addedCount = setOps.add(key, modifyDto.memberId.toString())
            ?: throw BusinessSeverException(REPO_SAVE_FAIL)
        if (addedCount > 0) {
            // 새로 추가된 좋아요면 TTL 설정 (TTL 없으면 만료 안 됨)
            stringRedisTemplate.expire(key, 3, TimeUnit.HOURS)
        }
        return true
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        val setOps = stringRedisTemplate.opsForSet()
        setOps.remove(RedisKeyGenerator.getMathRepoKey(modifyDto.contentsId), modifyDto.memberId.toString())
            ?: throw BusinessSeverException(REPO_DELETE_FAIL)
        return 1L
    }
}