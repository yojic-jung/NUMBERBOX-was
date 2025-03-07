package com.kamcci.numberbox.app.usecase.member

/**
 * 팔로잉 및 팔로워 변경
 */
interface MemberFollowWriteCase {
    /**
     * 팔로잉
     *
     * @param followingId - 팔로잉 하는 상대방 프로필 id
     * @param followerId - 팔로워 프로필 id
     */
    fun following(followingId: Long, followerId: Long)

    /**
     * 팔로잉 취소
     *
     * @param followingId - 팔로잉 하는 상대방 프로필 id
     * @param followerId - 팔로워 프로필 id
     */
    fun cancel(followingId: Long, followerId: Long)

}