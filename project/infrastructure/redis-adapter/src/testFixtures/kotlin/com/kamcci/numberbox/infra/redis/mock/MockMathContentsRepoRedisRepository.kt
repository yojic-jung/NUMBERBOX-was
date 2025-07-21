package com.kamcci.numberbox.infra.redis.mock

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsRepoRedisRepository
import org.springframework.data.redis.core.StringRedisTemplate

class MockMathContentsRepoRedisRepository: MathContentsRepoRedisRepository(StringRedisTemplate()) {
    var executeCnt = 0

    override fun save(modifyDto: MathContentsRepoModifyDto): Boolean {
        executeCnt++
        return true
    }

    override fun delete(modifyDto: MathContentsRepoModifyDto): Long {
        executeCnt++
        return 1L
    }
}