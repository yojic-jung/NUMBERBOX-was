package com.kamcci.numberbox.app.domain.vo.member

/**
 * 회원가입 결과 반환
 */
data class MemberVerifyCodeResultVo(
    val isSuccess: Boolean,
    val messageType: VerifyResultMSg,
) {
    enum class VerifyResultMSg(val desc: String) {
        VERIFY_SUCCESS("인증에 성공하였습니다"),
        NOT_EXIST("인증 코드가 존재하지 않습니다."),
        EXPIRED_MSG("만료된 인증 코드입니다."),
        NOT_MATCH_CODE_MSG("인증 코드가 일치하지 않습니다."),
    }

    fun getMessage() = messageType.desc
}

