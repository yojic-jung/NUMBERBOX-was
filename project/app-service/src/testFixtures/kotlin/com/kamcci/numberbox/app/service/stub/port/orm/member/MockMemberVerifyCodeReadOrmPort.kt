package com.kamcci.numberbox.app.service.stub.port.orm.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import java.time.LocalDateTime

class MockMemberVerifyCodeReadOrmPort : MemberVerifyCodeReadOrmPort {
    override fun countByEmailAndCodeType(email: String, codeType: VerifyCodeType): Long {
        return if (email == FAIL_EMAIL) 0L else 1L
    }

    override fun readByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberVerifyCodeVo? {
        return if (email == FAIL_EMAIL) null else MemberVerifyCodeVo("verifyCode", LocalDateTime.now())
    }
}