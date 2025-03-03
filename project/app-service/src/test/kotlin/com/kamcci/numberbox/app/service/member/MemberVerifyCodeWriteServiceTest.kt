package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.service.mock.port.email.MockEmailMessageTemplate
import com.kamcci.numberbox.app.service.mock.port.email.member.MockMemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

class MemberVerifyCodeWriteServiceTest {
    private val memberVerifyCodeSaveService = MemberVerifyCodeWriteService(
        MockMemberVerifyCodeWriteOrmPort(),
        MockMemberVerifyCodeEmailPort(),
        MockEmailMessageTemplate()
    )

    @Test
    fun `인증코드 생성 - 성공`() {
        // when
        val code = memberVerifyCodeSaveService.createVerifyCode("any", VerifyCodeType.SignUp)

        // then - uuid 문자열 검증
        assertDoesNotThrow {
            UUID.fromString(code)
        }
    }
}