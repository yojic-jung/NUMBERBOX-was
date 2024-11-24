package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto

/**
 * 사용자 정보 변경
 */
interface MemberPrivateWriteCase {
    /**
     * 휴대폰 번호 변경
     */
    fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean
}