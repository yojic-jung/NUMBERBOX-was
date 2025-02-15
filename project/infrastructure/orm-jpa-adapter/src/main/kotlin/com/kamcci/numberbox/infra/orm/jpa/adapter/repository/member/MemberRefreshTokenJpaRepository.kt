package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import org.springframework.stereotype.Repository

@Repository
class MemberRefreshTokenJpaRepository : BaseRepository() {

    fun save(memberRefreshTokenEntity: MemberRefreshTokenEntity): Long {
        em.persist(memberRefreshTokenEntity)
        return memberRefreshTokenEntity.id
    }

    fun deleteByToken(token: String) {
        queryFactory.delete(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
    }
}
