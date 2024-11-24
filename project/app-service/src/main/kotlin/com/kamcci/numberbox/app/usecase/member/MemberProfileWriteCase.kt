package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileWriteCase {
    // 프로필 타입 변경
    fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType)

    // 프로필 이미지 변경
    fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto)

    // 닉네임 변경
    fun updateNicknameByMemberId(memberId: UUID, nickname: String)

    // 한글 파일 일일 다운로드 제한 횟수 변경
    fun updateHwpDownCnt(hwpDownCnt: Int): Long
}