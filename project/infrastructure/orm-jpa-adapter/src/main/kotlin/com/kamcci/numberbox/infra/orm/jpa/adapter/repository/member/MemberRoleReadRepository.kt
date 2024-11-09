package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRoleEntity.memberRoleEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleReadRepository : MemberRoleReadOrmPort, BaseRepository() {
    override fun readRoleByMemberId(memberId: UUID): List<String> =
        queryFactory.select(memberRoleEntity.roleName)
            .from(memberRoleEntity)
            .where(memberRoleEntity.member.id.eq(memberId))
            .fetch()
}