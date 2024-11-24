package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class MemberVerifyCodeModifyServiceTest {
    private val memberVerifyCodeSaveService = MemberVerifyCodeModifyService(mock(), mock(), mock())

    @Test
    fun `인증코드 생성 - 성공`() {
        // when
        val code = memberVerifyCodeSaveService.createVerifyCode("", VerifyCodeType.SignUp)

        // then
        assertThat(code.length).isEqualTo(36)
        assertThat(code.replace("-", "").length).isEqualTo(32)
    }
}