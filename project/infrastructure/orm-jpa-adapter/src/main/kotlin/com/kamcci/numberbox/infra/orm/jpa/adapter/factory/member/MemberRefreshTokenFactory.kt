package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import java.util.*

object MemberRefreshTokenFactory {
    fun getSaveEntity(refreshToken: String, userUniqId: UUID) =
        MemberRefreshTokenEntity()
            .apply {
                token = refreshToken
                this.memberId = userUniqId
            }
}
