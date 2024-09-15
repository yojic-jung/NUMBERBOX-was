package com.kamcci.numberbox.restapi.dto.request.member

import org.hibernate.validator.constraints.Length

/**
 * 회원 가입 요청 - 개인정보
 */
data class MemberPrivateSignupRequest(
    @field:Length(min = 2, max = 17, message = "이름은 최소 두글자 이상 최대 17글자 이하입니다.")
    val userName: String,
    @field:Length(min = 10, max = 11, message = "휴대폰 번호는 하이픈(-) 없이 숫자만 10글자에서 11글자 형식입니다.")
    val phoneNumber: String,
    @field:Length(min = 6, max = 6, message = "생년월일은 6글자입니다. ex) 930123")
    val birth: String,
)
