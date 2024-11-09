package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberFollowModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.FollowUserDomain
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberFollowEntity.memberFollowEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberFollowFactory
import org.springframework.stereotype.Repository

@Repository
class MemberFollowModifyRepository : MemberFollowModifyOrmPort, BaseRepository() {
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