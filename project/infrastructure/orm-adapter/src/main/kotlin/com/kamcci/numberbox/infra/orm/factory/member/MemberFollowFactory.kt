package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.infra.orm.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.entity.member.MemberFollowEntity

object MemberFollowFactory {
    fun getSaveEntity(followingId: Long, followerId: Long): MemberFollowEntity {
        val followerDomain = FollowUserDomain(followingId, followerId)
        return MemberFollowEntity().apply {
            id = followerDomain
        }
    }
}