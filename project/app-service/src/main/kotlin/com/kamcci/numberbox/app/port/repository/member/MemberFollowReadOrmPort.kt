package com.kamcci.numberbox.app.port.repository.member

/**
 * 팔로잉 및 팔로워 조회
 */
interface MemberFollowReadOrmPort {
    // 팔로워 프로필 id로 팔로잉 프로필 id 조회
    fun findFollowingByFollower(profileId: Long): List<Long>

    // 팔로잉 프로필 id로 팔로워 프로필 id 조회
    fun findFollowerByFollowing(profileId: Long): List<Long>

    // 팔로워 수 조회
    fun countFollower(followingId: Long): Long

    // 팔로우 존재 여부 조회
    fun existFollow(followingId: Long, followerId: Long): Boolean
}