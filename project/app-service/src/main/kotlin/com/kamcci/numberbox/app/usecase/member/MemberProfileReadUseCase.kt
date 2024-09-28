package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import java.util.*

/**
 * 프로필 조회
 */
interface MemberProfileReadUseCase {
    // 프로필 가져오기
    fun findByMemberId(memberId: UUID): MemberProfileVo?

    // 프로필 이미지 가져오기
    fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo?
}