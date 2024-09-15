package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.infra.orm.entity.member.MemberRefreshTokenEntity
import java.util.*

object MemberRefreshTokenFactory {
    fun getSaveEntity(refreshToken: String, userUniqId: UUID) =
        MemberRefreshTokenEntity()
            .apply {
                token = refreshToken
                this.memberId = userUniqId
            }
}
