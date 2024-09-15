package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.app.domain.enumeration.ProfileType
import com.kamcci.numberbox.infra.orm.entity.member.MemberProfileEntity
import java.util.*

object MemberProfileFactory {
    fun getSaveEntity(uuid: UUID, nickName: String) =
        MemberProfileEntity()
            .apply {
                userUniqId = uuid
                this.nickname = nickName
                // 최초 등록시 프로필 타입은 미등록으로 설정
                profileType = ProfileType.None
            }

}