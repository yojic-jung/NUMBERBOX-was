package com.kamcci.numberbox.restapi.dummy.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

object MemberProfileFixture {
    fun getMemberProfileVo(): MemberProfileVo {
        return MemberProfileVo(
            id = 1L,
            memberId = UUID.randomUUID(),
            nickname = "",
            profileImgName = "",
            profileImgPath = "",
            profileType = ProfileType.Etc,
        )

    }
}