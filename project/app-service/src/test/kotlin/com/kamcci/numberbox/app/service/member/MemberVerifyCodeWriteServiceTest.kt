package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.service.mock.port.email.MockEmailMessageTemplate
import com.kamcci.numberbox.app.service.mock.port.email.member.MockMemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberVerifyCodeWriteOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MemberVerifyCodeWriteServiceTest {
    private val memberVerifyCodeSaveService = MemberVerifyCodeWriteService(
        MockMemberVerifyCodeWriteOrmPort(),
        MockMemberVerifyCodeEmailPort(),
        MockEmailMessageTemplate()
    )

    @Test
    fun `인증코드 생성 - 성공`() {
        // when
        val code = memberVerifyCodeSaveService.createVerifyCode("", VerifyCodeType.SignUp)

        // then
        assertThat(code.length).isEqualTo(36)
        assertThat(code.replace("-", "").length).isEqualTo(32)
    }
}