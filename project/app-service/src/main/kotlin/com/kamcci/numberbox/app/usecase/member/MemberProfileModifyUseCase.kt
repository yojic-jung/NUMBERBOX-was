package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.io.File
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyUseCase {
    // 프로필 타입 변경
    fun modifyProfileTypeByMemberId(memberId: UUID, profileType: ProfileType)

    // 프로필 이미지 변경
    fun modifyImgByMemberId(memberId: UUID, file: File)

    // 닉네임 변경
    fun modifyNicknameByMemberId(memberId: UUID)

}