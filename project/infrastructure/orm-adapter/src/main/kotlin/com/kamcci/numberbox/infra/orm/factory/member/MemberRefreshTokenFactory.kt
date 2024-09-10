package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.infra.orm.entity.member.MemberRefreshTokenEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class MemberRefreshTokenFactory {
    fun getSaveEntity(refreshToken: String, userUniqId: UUID) =
        MemberRefreshTokenEntity()
            .apply {
                token = refreshToken
                this.userUniqId = userUniqId
            }
}
