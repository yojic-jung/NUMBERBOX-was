package com.kamcci.numberbox.restapi.mock.auth

import com.kamcci.modules.auth.control.util.AuthPasswordEncoder

class MockAuthPasswordEncoder : AuthPasswordEncoder {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var excuteCnt = 0

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        excuteCnt++
        return true
    }

    override fun encode(rawPassword: CharSequence?): String {
        excuteCnt++
        return ""
    }
}