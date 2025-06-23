package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member

data class MemberRoleRedis(
    val roleName: String,
    val enabled: Boolean
)