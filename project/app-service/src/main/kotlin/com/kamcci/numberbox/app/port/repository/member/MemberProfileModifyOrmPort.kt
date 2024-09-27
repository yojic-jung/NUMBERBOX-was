package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyOrmPort {
    // 프로필 타입 변경
    fun modifyProfileTypeByMemberId(memberId: UUID, profileType: ProfileType)

    // 프로필 이미지 변경
    fun modifyImgByMemberId()

    // 닉네임 변경
    fun modifyNicknameByMemberId()
}