package com.kamcci.numberbox.infra.redis.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessServerException
import com.kamcci.numberbox.app.domain.vo.math.MathLikeCountVo
import com.kamcci.numberbox.infra.redis.adapter.common.RedisKeyGenerator
import com.kamcci.numberbox.infra.redis.adapter.common.RedisKeyGenerator.getMathContentsLikeCountKey
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
        const val LIKE_TTL = 3L
    }

    fun hasLikeKey(contentsId: Long): Boolean {
        return stringRedisTemplate.hasKey(RedisKeyGenerator.getMathContentsLikeKey(contentsId))
    }


    fun cacheLikeMember(contentsId: Long, memberIds: List<UUID>) {
        val key = RedisKeyGenerator.getMathContentsLikeKey(contentsId)
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
        // 1. userId 하위 contentsId(유저가 누른 전체 좋아요 컨텐츠 id)
        val userKey = RedisKeyGenerator.getUserContentsLike(modifyDto.memberId)
        setOps.add(userKey, modifyDto.contentsId.toString())
            ?: throw BusinessServerException(LIKE_SAVE_FAIL)


        // 2. contentsId 하위 userId 저장(컨텐츠에 신규로 좋아요 누른 사용자 목록)
        val key = RedisKeyGenerator.getMathContentsLikeKey(modifyDto.contentsId)
        setOps.add(key, modifyDto.memberId.toString())
            ?: throw BusinessServerException(LIKE_SAVE_FAIL)


        // 3. likeCount+1
        val contentsIdCountKey = getMathContentsLikeCountKey(modifyDto.contentsId)
        stringRedisTemplate.opsForValue().increment(contentsIdCountKey)
        return true
    }

    fun delete(modifyDto: MathContentsLikeModifyDto): Long {
        val setOps = stringRedisTemplate.opsForSet()
        // 1. userId 하위 contentsId(유저가 누른 전체 좋아요 컨텐츠 id)
        setOps.remove(
            RedisKeyGenerator.getUserContentsLike(modifyDto.memberId),
            modifyDto.contentsId.toString()
        )
            ?: throw BusinessServerException(LIKE_DELETE_FAIL)

        // 2. contentsId 하위 userId 삭제(컨텐츠에 신규로 좋아요 누른 사용자 목록)
        val removeCount = setOps.remove(
            RedisKeyGenerator.getMathContentsLikeKey(modifyDto.contentsId),
            modifyDto.memberId.toString()
        )
            ?: throw BusinessServerException(LIKE_DELETE_FAIL)

        // 3. likeCount-1
        val contentsIdCountKey = getMathContentsLikeCountKey(modifyDto.contentsId)
        stringRedisTemplate.opsForValue().decrement(contentsIdCountKey)
        return removeCount
    }

    fun countBy(contentsIds: List<Long>): List<MathLikeCountVo> {
        val setOps = stringRedisTemplate.opsForSet()

        return contentsIds.mapNotNull { contentsId ->
            val key = RedisKeyGenerator.getMathContentsLikeKey(contentsId)
            val size = setOps.size(key)
            if (size == null) null
            else MathLikeCountVo(contentsId, size)
        }
    }

    fun saveLikeCount(dbCounts: List<MathLikeCountVo>) {
        dbCounts.forEach { countVo ->
            val key = RedisKeyGenerator.getMathContentsLikeKey(countVo.contentsId)
            stringRedisTemplate.opsForValue().set(key, countVo.count.toString(), LIKE_TTL, TimeUnit.HOURS)
        }
    }

    fun readContentsIdByUserId(userId: UUID): List<Long> {
        val key = RedisKeyGenerator.getUserContentsLike(userId)
        val members = stringRedisTemplate.opsForSet().members(key) ?: emptySet()
        return members.mapNotNull { it.toLongOrNull() }
    }

    fun saveUserLike(userId: UUID, contentsIdList: List<Long>) {
        val key = RedisKeyGenerator.getUserContentsLike(userId)
        val setOps = stringRedisTemplate.opsForSet()
        val members = contentsIdList.map { it.toString() }.toTypedArray()

        setOps.add(key, *members)
        stringRedisTemplate.expire(key, LIKE_TTL, TimeUnit.HOURS)
    }


}