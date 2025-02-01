package com.kamcci.numberbox.app.service.constant

import java.util.*

/**
 * 실패 케이스 공용 상수
 *
 * - 메서드 인풋 파라미터 실패 값(동시성 제어를 위해 메서드 파라미터 값에 따라 목객체의 처리를 달리하기 위함)
 */
object FailConstant {
    // 실패 케이스 id
    val FAIL_ID = 2L

    // 실패케이스 email
    val FAIL_EMAIL = "fail@test.com"

    // 실피케이스 userName
    val FAIL_USER_NAME = "실패자"

    // 실패케이스 MEMBER_ID
    val FAIL_MEMBER_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    // 문자열 파라미터 실패
    val FAIL_STRING = "실패"

}