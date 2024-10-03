package com.kamcci.numberbox.restapi.dto.request.member

import org.hibernate.validator.constraints.Length
import java.util.*

/**
 * 회원 휴대폰 번호 변경 request
 */
data class MemberPhoneUpdtRequest(
    val verifyCode: UUID,
    @field:Length(min = 10, max = 11, message = "휴대폰 번호는 하이픈(-) 없이 숫자만 10글자에서 11글자 형식입니다.")
    val phoneNumber: String,
)