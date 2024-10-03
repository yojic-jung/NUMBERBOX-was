package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.PhoneCheck
import java.util.*

/**
 * 회원 휴대폰 번호 변경 request
 */
data class MemberPhoneUpdtRequest(
    val verifyCode: UUID,
    @field:PhoneCheck
    val phoneNumber: String,
)