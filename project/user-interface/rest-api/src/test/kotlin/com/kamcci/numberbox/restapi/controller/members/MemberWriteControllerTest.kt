package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.resolver.MockUserDetailArgumentResolver.Companion.EMAIL_FROM_RESOLVER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException

@WebMvcUnitTest
class MemberWriteControllerTest : BaseMockMvcTest() {

    @Autowired
    lateinit var memberWriteCase: MemberWriteCase

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
            "previousPassword" to "asdfaf",
            "password" to "abcdefgh1234!",
            "passwordConfirm" to "abcdefgh!",
        )

        // when
        val resultAction = putRequest(PASSWORD_URL, req)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
    }


    @Test
    fun `비밀번호 변경 - 성공`() {
        // given
        val req = mapOf(
            "previousPassword" to "sadfjl123",
            "password" to "abcdefgh1234!",
            "passwordConfirm" to "abcdefgh1234!",
        )
        `when`(memberWriteCase.updatePassword(any())).thenReturn(true)

        // when
        val resultAction = putRequest(PASSWORD_URL, req)

        // then
        assert2xx(resultAction)
    }
}