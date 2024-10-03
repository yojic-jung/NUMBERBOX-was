package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.repository.member.MemberFollowReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.entity.member.QMemberFollowEntity.memberFollowEntity
import org.springframework.stereotype.Repository

@Repository
class MemberFollowReadOrmAdapter : MemberFollowReadOrmPort, BaseRepository() {
    override fun findFollowingByFollower(profileId: Long): List<Long> {
        return queryFactory
            .select(memberFollowEntity.id.followingUserNo)
            .from(memberFollowEntity)
            .where(memberFollowEntity.id.followerUserNo.eq(profileId))
            .fetch()
    }

    override fun findFollowerByFollowing(profileId: Long): List<Long> {
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