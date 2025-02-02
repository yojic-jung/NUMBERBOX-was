package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.http.converter.HttpMessageNotReadableException

@WebMvcUnitTest
class MemberWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/member"
        const val PASSWORD_URL = "$PREFIX/password"
        const val PASSWORD_CONFIRM_URL = "$PREFIX/password-confirm"
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

        // when
        val resultAction = putRequest(PASSWORD_URL, req)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `비밀번호 확인 - 성공`() {
        // given
        val req = mapOf("password" to "password")

        // when
        val resultAction = postRequest(PASSWORD_CONFIRM_URL, req)

        // then
        assert2xx(resultAction)
    }
}