package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberFollowEntity.memberFollowEntity
import org.springframework.stereotype.Repository

@Repository
class MemberFollowReadRepository : MemberFollowReadCase, BaseRepository() {
    override fun readFollowingByFollower(profileId: Long): List<Long> {
        return queryFactory
            .select(memberFollowEntity.id.followingUserNo)
            .from(memberFollowEntity)
            .where(memberFollowEntity.id.followerUserNo.eq(profileId))
            .fetch()
    }

    override fun readFollowerByFollowing(profileId: Long): List<Long> {
        return queryFactory
            .select(memberFollowEntity.id.followerUserNo)
            .from(memberFollowEntity)
            .where(memberFollowEntity.id.followingUserNo.eq(profileId))
            .fetch()
    }

    override fun countFollower(followingId: Long): Long {
        return queryFactory
            .select(memberFollowEntity.id.count())
            .from(memberFollowEntity)
            .where(memberFollowEntity.id.followingUserNo.eq(followingId))
            .fetchFirst()
    }

    override fun existFollow(followingId: Long, followerId: Long): Boolean {
        val followDomain = FollowUserDomain(followingId, followerId)
        return queryFactory
            .selectOne()
            .from(memberFollowEntity)
            .where(memberFollowEntity.id.eq(followDomain))
            .fetchOne() != null
    }
}