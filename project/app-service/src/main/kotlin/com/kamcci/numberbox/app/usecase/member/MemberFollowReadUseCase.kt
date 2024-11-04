package com.kamcci.numberbox.app.usecase.member

/**
 * 팔로잉 및 팔로워 조회
 */
interface MemberFollowReadUseCase {
    // 팔로워 프로필 id로 팔로잉 프로필 id 조회
    fun readFollowingByFollower(profileId: Long): List<Long>

    // 팔로잉 프로필 id로 팔로워 프로필 id 조회
    fun readFollowerByFollowing(profileId: Long): List<Long>

    // 팔로워 수 조회
    fun countFollower(followingId: Long): Long

    // 팔로잉 했는지 여부
    fun isFollowing(follwingId: Long, followerId: Long): Boolean
}