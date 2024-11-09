package com.kamcci.numberbox.app.port.orm.member

/**
 * 팔로잉 및 팔로워 조회
 */
interface MemberFollowModifyOrmPort {
    // 팔로잉
    fun save(followingId: Long, followerId: Long): Boolean

    // 팔로잉 취소
    fun delete(followingId: Long, followerId: Long): Boolean
}
