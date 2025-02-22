package com.kamcci.numberbox.app.domain.enumeration.member

enum class VerifyCodeType(val dbData: Int, val title: String) {
    SignUp(1, "회원가입"),
    Password(2, "비밀번호 변경"),
    PhoneNumber(3, "휴대폰 번호 변경"),
}