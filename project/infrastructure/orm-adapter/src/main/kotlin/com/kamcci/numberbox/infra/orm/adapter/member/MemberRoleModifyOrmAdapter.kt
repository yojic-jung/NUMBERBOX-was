package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.port.repository.member.MemberRoleModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberRoleEntity.memberRoleEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRoleModifyOrmAdapter : MemberRoleModifyOrmPort, BaseRepository() {
    override fun updateEnabledById(id: UUID, enabled: Boolean) =
        queryFactory
            .update(memberRoleEntity)
            .set(memberRoleEntity.enabled, enabled)
            .where(memberRoleEntity.member.id.eq(id))
            .execute() > 0
}