package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import com.kammci.numberbox.restapi.resolver.MockUserDetailArgumentResolver.Companion.EMAIL_FROM_RESOLVER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@WebMvcUnitTest
class MemberControllerTest : BaseMockMvcTest() {

    @Autowired
    lateinit var memberModifyUseCase: MemberModifyUseCase

    companion object {
        private const val EMAIl_URL = "/member/email"
        private const val PASSWORD_URL = "/member/password"
    }

    @Test
    fun `이메일 조회 - 성공`() {
        // when
        val resultAction = getRequest(EMAIl_URL)

        // then
        assertThat(removeQuotes(takeJsonResponseData(resultAction).get("email"))).isEqualTo(EMAIL_FROM_RESOLVER)
    }

    @Test
    fun `비밀번호 변경 - 실패(비밀번호 불일치)`() {
        // given
        val req = mapOf(
            "verifyCode" to UUID.randomUUID(),
            "password" to "abcdefgh1234!",
            "passwordConfirm" to "abcdefgh1234!",
        )

        // when
        val resultAction = putRequest(PASSWORD_URL, req)

        // then
        assertThat(removeQuotes(takeJsonResponseData(resultAction).get("isSuccess"))).isEqualTo("false")
    }


    @Test
    fun `비밀번호 변경 - 성공`() {
        // given
        val req = mapOf(
            "verifyCode" to UUID.randomUUID(),
            "password" to "abcdefgh1234!",
            "passwordConfirm" to "abcdefgh1234!",
        )
        `when`(memberModifyUseCase.updatePassword(any())).thenReturn(true)

        // when
        val resultAction = putRequest(PASSWORD_URL, req)

        // then
        assertThat(removeQuotes(takeJsonResponseData(resultAction).get("isSuccess"))).isEqualTo("false")
    }


}