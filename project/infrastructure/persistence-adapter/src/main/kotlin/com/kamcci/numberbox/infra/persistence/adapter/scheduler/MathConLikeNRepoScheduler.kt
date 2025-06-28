package com.kamcci.numberbox.infra.persistence.adapter.scheduler//package com.kamcci.numberbox.infra.persistence.adapter.repository

import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math.MathContentsLikeWriteNativeQuery
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames.MATH_CONTENTS_LIKE
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames.MATH_CONTENTS_LIKE_WILD_CARD
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames.MATH_CONTENTS_REPO
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames.MATH_CONTENTS_REPO_WILD_CARD
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames.SEPARATOR
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.Cursor
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Primary
@Component
class MathConLikeNRepoScheduler(
    private val stringRedisTemplate: StringRedisTemplate,
    private val mathContentsLikeWriteNativeQuery: MathContentsLikeWriteNativeQuery
) {
    // 좋아요 정보 rdb로 이관
    @Scheduled(cron = "0 0 0/2 * * *")
    fun mathLikeBulkInsert() {
        val keyList = getAllKey(MATH_CONTENTS_LIKE_WILD_CARD)
        keyList.forEach { contentsIdStr ->
            val memberIdSet = stringRedisTemplate.opsForSet().members(contentsIdStr) ?: emptySet()
            val contentsId = contentsIdStr.removePrefix(MATH_CONTENTS_LIKE + SEPARATOR).toLong()

            // 1) RDB 이관
            mathContentsLikeWriteNativeQuery.bulkInsertLikes(contentsId, memberIdSet.toList())

            // 2) 이관 성공 후 Redis 데이터 삭제
            stringRedisTemplate.delete(contentsIdStr)
        }
    }

    // 저장소 정보 rdb로 이관
    @Scheduled(cron = "0 0 0/2 * * *")
    fun mathRepoBulkInsert() {
        val keyList = getAllKey(MATH_CONTENTS_REPO_WILD_CARD)
        keyList.forEach { contentsIdStr ->
            val memberIdSet = stringRedisTemplate.opsForSet().members(contentsIdStr) ?: emptySet()
            val contentsId = contentsIdStr.removePrefix(MATH_CONTENTS_REPO + SEPARATOR).toLong()

            // 1) RDB 이관
            mathContentsLikeWriteNativeQuery.bulkInsertRepo(contentsId, memberIdSet.toList())

            // 2) 이관 성공 후 Redis 데이터 삭제
            stringRedisTemplate.delete(contentsIdStr)
        }
    }

    // 키 패턴으로 모든 키 조회
    private fun getAllKey(keyPattern: String): List<String> {
        val keys = mutableListOf<String>()
        var cursor: Cursor<String>? = null

        try {
            cursor = stringRedisTemplate.scan(
                ScanOptions.scanOptions()
                    .match(keyPattern)
                    .count(1000)
                    .build()
            )
            cursor.forEach { key ->
                keys.add(key)
            }
        } finally {
            cursor?.close()
        }
        return keys
    }

}