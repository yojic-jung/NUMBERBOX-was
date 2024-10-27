package com.kamcci.numberbox.restapi.exception.code

import com.kamcci.numberbox.app.domain.exception.code.BaseErrCodeType

/**
 * 100번대 restApi모듈 에러코드(상태 코드 아닌 에러 코드)
 */
enum class RestApi100ErrCodeType(
    override val code: Int,
    override val message: String,
) : BaseErrCodeType {
    DISABLE_USER(100, "해당 계정이 잠금 되었습니다. 15분 후 다시 시도해주세요."),
    DISABLE_TO_ABLE(101, "해당 계정의 15분 잠금이 풀렸습니다.\n다시 로그인 시도해주세요."),
    PASSWORD_MISS_MATCH(102, "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.")
}