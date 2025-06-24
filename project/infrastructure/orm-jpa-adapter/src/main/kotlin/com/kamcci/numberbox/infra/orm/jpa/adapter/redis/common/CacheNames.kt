package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common

/**
 * 캐시DB 키 네임
 */
object CacheNames {
    const val SEPARATOR = ":"

    // 토큰
    const val REFRESH_TOKEN = "refreshToken"

    // 회원
    const val MEMBER = "member"
    const val MEMBER_EMAIL = MEMBER + SEPARATOR + "email"

    // 수학
    const val MATH = "math"
    const val MATH_CONTENTS = MATH + SEPARATOR + "contents"

    // 좋아요
    const val MATH_CONTENTS_LIKE = MATH_CONTENTS + SEPARATOR + "like"

    // 저장소
    const val MATH_CONTENTS_REPO = MATH_CONTENTS + SEPARATOR + "repo"

}