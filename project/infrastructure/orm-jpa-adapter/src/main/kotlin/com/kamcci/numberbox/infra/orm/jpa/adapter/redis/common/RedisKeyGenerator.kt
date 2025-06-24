package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common

import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames.MATH_CONTENTS_LIKE
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames.MATH_CONTENTS_REPO
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames.SEPARATOR

object RedisKeyGenerator {
    fun getMathLikeKey(mathContentsId: Long) = MATH_CONTENTS_LIKE + SEPARATOR + mathContentsId

    fun getMathRepoKey(mathContentsId: Long) = MATH_CONTENTS_REPO + SEPARATOR + mathContentsId
}