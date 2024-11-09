package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberFollowEntity

object MemberFollowFactory {
    fun getSaveEntity(followingId: Long, followerId: Long): MemberFollowEntity {
        val followerDomain = FollowUserDomain(followingId, followerId)
        return MemberFollowEntity().apply {
            id = followerDomain
        }
    }
}