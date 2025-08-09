package com.kamcci.numberbox.infra.redis.adapter.common

import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.MATH_CONTENTS_LIKE
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.MATH_CONTENTS_LIKE_COUNT
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.MATH_CONTENTS_REPO
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.SEPARATOR
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames.USER_CONTENTS_LIKE
import java.util.*


/**
 * redis 키 생성
 */
object RedisKeyGenerator {

    /**
     * 사용자가 누른 좋아요 컨텐츠 목록
     * key : userId
     * value : contentsId
     */
    fun getUserContentsLike(userId: UUID) = USER_CONTENTS_LIKE.replace("{}", userId.toString())

    /**
     * 컨텐츠에 신규로 좋아요 누른 사용자 목록
     * key : contentsId
     * value : userId
     */
    fun getMathContentsLikeKey(mathContentsId: Long) = MATH_CONTENTS_LIKE + SEPARATOR + SEPARATOR + mathContentsId

    fun getMathContentsLikeCountKey(mathContentsId: Long) =
        MATH_CONTENTS_LIKE_COUNT + SEPARATOR + SEPARATOR + mathContentsId

    fun getMathRepoKey(mathContentsId: Long) = MATH_CONTENTS_REPO + SEPARATOR + mathContentsId
}