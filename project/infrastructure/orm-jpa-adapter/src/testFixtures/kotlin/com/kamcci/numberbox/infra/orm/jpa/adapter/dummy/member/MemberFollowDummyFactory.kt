package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member

object MemberFollowDummyFactory {
    const val NOT_EXIST_FOLLOW_ID = 999999L

    // 조회목적
    fun getMemberFollowDummyEntity() =
        ExistEntityInfo(3L, 4L)

    // 삭제 목적
    fun getMemberFollowDummyEntity4Del() =
        ExistEntityInfo(6L, 7L)

    class ExistEntityInfo(val followingUserNo: Long, val followerUserNo: Long)
}