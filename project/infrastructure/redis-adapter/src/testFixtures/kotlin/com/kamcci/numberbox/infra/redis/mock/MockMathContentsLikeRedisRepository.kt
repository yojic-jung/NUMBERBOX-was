package com.kamcci.numberbox.infra.redis.mock

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.UUID

class MockMathContentsLikeRedisRepository: MathContentsLikeRedisRepository(StringRedisTemplate()) {

    var executeCnt = 0

    override fun save(modifyDto: MathContentsLikeModifyDto): Boolean {
        executeCnt++
        return true
    }

    override fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        executeCnt++
        return 1L
    }

    override fun hasLikeKey(contentsId: Long): Boolean {
        return if(contentsId == EXIST_ID) true
        else false
    }

    override fun cacheLikeMember(contentsId: Long, memberIds: List<UUID>) {

    }
}