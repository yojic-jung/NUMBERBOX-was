package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyUseCase {
    // 프로필 타입 변경
    fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Boolean

    // 프로필 이미지 변경
    fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto): FileNameVo

    // 닉네임 변경
    fun updateNicknameByMemberId(memberId: UUID, nickname: String): Boolean

    // 한글 파일 일일 다운로드 제한 횟수 변경
    fun updateHwpDownCnt(hwpDownCnt: Int): Long
}