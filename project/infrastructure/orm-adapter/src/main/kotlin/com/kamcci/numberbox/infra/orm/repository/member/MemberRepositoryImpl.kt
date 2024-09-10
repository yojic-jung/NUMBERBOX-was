package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberRepositoryImpl : BaseRepository() {
    fun updateSuccessUser(
        userUniqId: UUID,
        failCount: Int,
        humanStatus: Int,
    ) {
        queryFactory
            .update(memberEntity)
            .set(memberEntity.lastLoginDate, LocalDateTime.now())
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.humanStatus, humanStatus)
            .where(memberEntity.userUniqId.eq(userUniqId))
    }
}
