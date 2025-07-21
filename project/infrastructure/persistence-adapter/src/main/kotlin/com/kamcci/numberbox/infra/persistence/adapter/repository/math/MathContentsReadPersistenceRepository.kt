package com.kamcci.numberbox.infra.persistence.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeReadRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsReadRepository
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.EXIST_MATH_CONTENTS
import com.kamcci.numberbox.infra.redis.adapter.repository.math.MathContentsLikeRedisRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class MathContentsReadPersistenceRepository(
    private val mathContentsReadRepository: MathContentsReadRepository,
    private val mathContentsLikeReadRepository: MathContentsLikeReadRepository,
    private val mathContentsLikeRedisRepository: MathContentsLikeRedisRepository
) : MathContentsReadCase {
    override fun readById(contentsId: Long): MathContentsVo? {
        // 게시글 상세 조회시 - 좋아요 정보 레디스에 캐싱
        likeCaching(contentsId)
        return mathContentsReadRepository.readById(contentsId)
    }


    override fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo> {
        return mathContentsReadRepository.readById(contentsId, pageReq)
    }

    override fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo? {
        return mathContentsReadRepository.readDetailByContentsIdAndMemberId(id, memberId)
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return mathContentsReadRepository.readDetailByMemberId(memberId, svcPosbSttsType, pageReq)
    }

    override fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return mathContentsReadRepository.readDetailByMemberId(memberId, myMemberId, svcPosbSttsType, pageReq)
    }

    override fun readDetailByUnitId(
        memberId: UUID,
        unitId: List<Int>,
        pageReq: PageRequest
    ): List<MathContentsDetailVo> {
        return mathContentsReadRepository.readDetailByUnitId(memberId, unitId, pageReq)
    }

    override fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo? {
        // 게시글 상세 조회시 - 좋아요 정보 레디스에 캐싱
        likeCaching(contentsId)
        return mathContentsReadRepository.readInHouseContentsById(contentsId)
    }

    override fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo? {
        // 게시글 상세 조회시 - 좋아요 정보 레디스에 캐싱
        likeCaching(contentsId)
        return mathContentsReadRepository.readIpsiContentsById(contentsId)
    }

    override fun readTransContCntById(id: Long): Int? {
        return mathContentsReadRepository.readTransContCntById(id)
    }

    override fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo? {
        // 게시글 상세 조회시 - 좋아요 정보 레디스에 캐싱
        likeCaching(contentsId)
        return mathContentsReadRepository.readContentsOnly(contentsId, memberId)
    }

    private fun likeCaching(contentsId: Long) {
        // 게시글 상세 조회시 - 좋아요 정보 레디스에 캐싱
        if (!mathContentsLikeRedisRepository.hasLikeKey(contentsId)) {
            val memberIdList = mathContentsLikeReadRepository.readMemberIdListById(contentsId)
            mathContentsLikeRedisRepository.cacheLikeMember(contentsId, memberIdList)
        }
    }


    override fun countByUnitId(unitId: List<Int>): Long {
        return mathContentsReadRepository.countByUnitId(unitId)
    }

    @Cacheable(
        cacheNames = [EXIST_MATH_CONTENTS],
        key = "#id",
        unless = "!#result"
    )
    override fun existById(id: Long): Boolean {
        return mathContentsReadRepository.existById(id)
    }
}