package com.kamcci.numberbox.infra.redis.adapter.hash.member

data class MemberRoleRedis(
    val roleName: String,
    val enabled: Boolean
)