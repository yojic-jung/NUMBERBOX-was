package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.BirthCheck
import com.kamcci.numberbox.restapi.validation.member.PhoneCheck
import org.hibernate.validator.constraints.Length

/**
 * 회원 가입 요청 - 개인정보
 */
data class MemberPrivateSignupRequest(
    @field:Length(min = 2, max = 17, message = "이름은 최소 두글자 이상 최대 17글자 이하입니다.")
    val userName: String,
    @field:PhoneCheck
    val phoneNumber: String,
    @field:BirthCheck
    val birth: String,
)
