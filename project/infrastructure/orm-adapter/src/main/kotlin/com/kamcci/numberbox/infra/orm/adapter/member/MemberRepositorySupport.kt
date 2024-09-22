package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberRepositorySupport : BaseRepository() {
    fun updateSuccessUser(
        userUniqId: UUID,
        failCount: Int,
        humanStatus: Int,
    ): Boolean {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.lastLoginTime, LocalDateTime.now())
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.humanStatus, humanStatus)
            .where(memberEntity.id.eq(userUniqId))
            .execute() > 0
    }
}
