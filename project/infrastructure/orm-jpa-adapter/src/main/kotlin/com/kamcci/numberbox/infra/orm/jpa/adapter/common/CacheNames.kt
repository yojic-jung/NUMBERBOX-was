package com.kamcci.numberbox.infra.orm.jpa.adapter.common

/**
 * 캐시DB 키 네임
 */
object CacheNames {
    const val SEPARATOR = ":"

    // 회원
    const val MEMBER = "member"
    const val MEMBER_EMAIL = MEMBER + SEPARATOR + "email"

    const val REFRESH_TOKEN = "refreshToken"
}