package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberRepositorySupport : BaseRepository() {

    fun updateSuccessUser(
        userUniqId: UUID,
        failCount: Int,
        humanStatus: Int,
    ): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.lastLoginTime, LocalDateTime.now())
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.humanStatus, humanStatus)
            .where(memberEntity.id.eq(userUniqId))
            .execute()
    }
}
