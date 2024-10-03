package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.repository.member.MemberFollowModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.entity.member.QMemberFollowEntity.memberFollowEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberFollowFactory
import org.springframework.stereotype.Repository

@Repository
class MemberFollowModifyOrmAdapter : MemberFollowModifyOrmPort, BaseRepository() {
    override fun save(followingId: Long, followerId: Long): Boolean {
        val entity = MemberFollowFactory.getSaveEntity(followingId, followerId)
        em.persist(entity)
        return em.contains(entity)
    }

    override fun delete(followingId: Long, followerId: Long): Boolean {
        val followDomain = FollowUserDomain(followingId, followerId)
        return queryFactory
            .delete(memberFollowEntity)
            .where(memberFollowEntity.id.eq(followDomain))
            .execute() > 0
    }
}