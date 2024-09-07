package com.numberbox.app.usecase.member

interface MemberSignupUseCase {

    // 회원가입 절차에서 사용자 이메일 검증 목적
    fun createEmailCode(email: String): Boolean
}