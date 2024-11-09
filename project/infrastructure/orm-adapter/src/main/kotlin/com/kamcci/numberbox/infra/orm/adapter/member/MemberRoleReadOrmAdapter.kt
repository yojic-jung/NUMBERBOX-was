package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleReadOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberRoleEntity.memberRoleEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleReadOrmAdapter : MemberRoleReadOrmPort, BaseRepository() {
    override fun readRoleByMemberId(memberId: UUID): List<String> =
        queryFactory.select(memberRoleEntity.roleName)
            .from(memberRoleEntity)
            .where(memberRoleEntity.member.id.eq(memberId))
            .fetch()
}