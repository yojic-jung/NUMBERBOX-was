package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.vo.math.MathLikeCountVo
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeReadRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class MathContentsLikeReadPersistenceRepository(
    private val mathContentsLikeReadRepository: MathContentsLikeReadRepository,
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository
) : MathContentsLikeReadCase {
    override fun existByContentsIdAndMemberId(contentsId: Long, memberId: UUID): Boolean {
        return mathContentsLikeReadRepository.existByContentsIdAndMemberId(contentsId, memberId)
    }

    override fun countBy(contentsId: Long): Long {
        return mathContentsLikeReadRepository.countBy(contentsId)
    }

    override fun readContentsIdByUserId(userId: UUID): List<Long> {
        // 캐싱 존재시 캐시DB에서 조회
        val contentsIdList = mathContentsLikeRedisRepository.readContentsIdByUserId(userId)

        // 미존재시 캐시 추가
        return if (contentsIdList.isEmpty()) {
            val list = mathContentsLikeReadRepository.readContentsIdByUserId(userId)
            mathContentsLikeRedisRepository.saveUserLike(userId, list)
            list
        } else contentsIdList
    }

    override fun countBy(contentsIds: List<Long>): List<MathLikeCountVo> {
        // 1. 캐시에서 좋아요 수 조회
        val cachedCounts = mathContentsLikeRedisRepository.countBy(contentsIds)

        // 캐시에서 조회된 contentsId 목록
        val cachedIds = cachedCounts.map { it.contentsId }.toSet()

        // 2. 캐시에 없는 contentsId 목록
        val missingIds = contentsIds.filterNot { cachedIds.contains(it) }
        if (missingIds.isEmpty()) {
            // 모두 캐시에 존재하면 바로 반환
            return cachedCounts
        }

        // 3. DB에서 missingIds에 대한 좋아요 수 조회
        val dbCounts = mathContentsLikeReadRepository.countBy(missingIds)

        // 4. 조회한 DB 결과를 Redis에 캐싱
        mathContentsLikeRedisRepository.saveLikeCount(dbCounts)

        // 5. 캐시 + DB 결과 합쳐서 반환
        return cachedCounts + dbCounts
    }
}