package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.member.PhoneCheck
import org.hibernate.validator.constraints.Length

/**
 * 이메일 찾기 request
 */
data class EmailFindRequest(
    @field:Length(min = 2, max = 17, message = "이름을 최소 두글자 이상 입력해주세요.")
    val userName: String,
    @field:PhoneCheck
    val phoneNumber: String,
)