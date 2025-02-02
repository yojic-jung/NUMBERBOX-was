package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberDropCase
import java.util.*

class MockMemberDropCase : MemberDropCase {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var isExceptionCase = false // 예외 발생 여부
    var executeCnt = 0 // 실행 카운트

    override fun drop(memberId: UUID) {
        if (memberId == EXCEPTION_MEMBER_ID || isExceptionCase) throw RuntimeException(STUB_EXCEPTION_MSG)
        executeCnt++
    }
}