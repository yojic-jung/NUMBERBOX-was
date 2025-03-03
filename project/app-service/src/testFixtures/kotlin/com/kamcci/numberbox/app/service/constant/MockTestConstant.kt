package com.kamcci.numberbox.app.service.constant

import java.util.*

/**
 * 테스트 공용 상수
 *
 * - 테스트 더블 메서드의 인자 값
 * - 인자값에 따라 성공/실패, 예외를 구분하기 위함
 */
object MockTestConstant {
    // 성공 ID
    val SUCCESS_ID = 1L

    // 영속화 성공
    val CREATE_SUCCESS_ID = 1L

    // 스텁에서 사용하는 예외 메시지
    val STUB_EXCEPTION_MSG = "실패 케이스 예외 발생"

    // 실패 id
    val FAIL_ID = 2L

    // exist 쿼리에서 true 반환할 ID
    val EXIST_ID = 1L

    val NOT_EXIST_ID = 2L

    // 예외 터트리는 ID
    val EXCEPTION_ID = 3L

    // 실패 email
    val FAIL_EMAIL = "fail@test.com"

    // exist 쿼리에서 true 반환할 email
    val EXIST_EMAIL = "exist@test.com"

    val NOT_EXIST_EMAIL = "notExist@test.com"

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