package com.kamcci.numberbox.restapi.dto.request.member

/**
 * 회원 비밀번호 확인 request
 */
data class MemberPasswdConfirmRequest(
    // 회원 비밀번호 확인 목적은 임시 비밀번호일 수 있으므로 유효성 검사 진행 안함
    val password: String,
)