package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRoleEntity.memberRoleEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleModifyRepository : MemberRoleModifyOrmPort, BaseRepository() {
    override fun updateEnabledById(id: UUID, enabled: Boolean) =
        queryFactory
            .update(memberRoleEntity)
            .set(memberRoleEntity.enabled, enabled)
            .where(memberRoleEntity.member.id.eq(id))
            .execute() > 0
}