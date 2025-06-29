package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class MathContentsLikeReadPersistenceRepository(
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository,
) : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return mathContentsLikeRedisRepository.existBy(contentsId, memberId)
    }

    override fun countBy(contentsId: Long): Long {
        return mathContentsLikeRedisRepository.countBy(contentsId)
    }
}