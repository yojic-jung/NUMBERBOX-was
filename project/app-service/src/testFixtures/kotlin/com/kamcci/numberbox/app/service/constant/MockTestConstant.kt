package com.kamcci.numberbox.app.service.constant

import java.util.*

/**
 * 테스트 공용 상수
 *
 * - 메서드 인풋 파라미터 실패 값(동시성 제어를 위해 메서드 파라미터 값에 따라 목객체의 처리를 달리하기 위함)
 */
object MockTestConstant {
    // 스텁에서 사용하는 예외 메시지
    val STUB_EXCEPTION_MSG = "실패 케이스 예외 발생"

    // 실패 id
    val FAIL_ID = 2L

    // 예외 터트리는 ID
    val EXCEPTION_ID = 3L

    // 실패 email
    val FAIL_EMAIL = "fail@test.com"

    // 예외 터트리는 email
    val EXCEPTION_EMAIL = "exception@test.com"

    // 실피 userName
    val FAIL_USER_NAME = "실패자"

    // 실패 MEMBER_ID
    val FAIL_MEMBER_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 예외 터트리는 MEMBER_ID
    val EXCEPTION_MEMBER_ID = UUID.fromString("10cf5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 실패 휴대폰 번호
    val FAIL_PHONE_NUMBER = "01099999999"

    // 실패 문자열 파라미터
    val FAIL_STRING = "실패"

}