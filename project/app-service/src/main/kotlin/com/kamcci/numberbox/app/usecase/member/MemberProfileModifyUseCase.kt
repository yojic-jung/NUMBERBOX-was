package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.io.File
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyUseCase {
    // 프로필 타입 변경
    fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Boolean

    // 프로필 이미지 변경
    fun updateImgByMemberId(memberId: UUID, file: File): Boolean

    // 닉네임 변경
    fun updateNicknameByMemberId(memberId: UUID, nickname: String): Boolean

}