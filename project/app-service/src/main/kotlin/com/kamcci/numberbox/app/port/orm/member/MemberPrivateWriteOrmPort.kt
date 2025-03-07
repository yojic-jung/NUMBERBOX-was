package com.kamcci.numberbox.app.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import java.util.*

/**
 * 회원 개인정보 - 변경
 */
interface MemberPrivateWriteOrmPort {
    // 개인정보 등록
    fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID

    // 휴대폰 번호 변경
    fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Long

    // 개인정보 파기
    fun updatePrivateToNull(memberId: UUID): Long

}