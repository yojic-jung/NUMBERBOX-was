package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import java.io.InputStream
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyUseCase {
    // 프로필 타입 변경
    fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Boolean

    // 프로필 이미지 변경
    fun updateImgByMemberId(memberId: UUID, fileName: String, inpStream: InputStream): FileNameVo

    // 닉네임 변경
    fun updateNicknameByMemberId(memberId: UUID, nickname: String): Boolean
}