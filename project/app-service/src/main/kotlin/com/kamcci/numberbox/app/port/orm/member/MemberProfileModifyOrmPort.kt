package com.kamcci.numberbox.app.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import java.util.*

/**
 * 프로필 변경
 */
interface MemberProfileModifyOrmPort {
    // 프로필 등록
    fun save(uuid: UUID, nickName: String): Long

    // 프로필 타입 변경
    fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Long

    // 프로필 이미지 변경
    fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto): Long

    // 닉네임 변경
    fun updateNicknameByMemberId(memberId: UUID, nickname: String): Long

    // 한글 파일 일일 다운로드 제한 횟수 변경
    fun updateHwpDownCntByMemberId(hwpDownCnt: Int): Long
}