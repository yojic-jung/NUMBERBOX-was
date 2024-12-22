package com.kamcci.numberbox.restapi.validation.member

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PasswordCheckValidatorTest {
    private val passwordCheckValidator =
        PasswordCheckValidator("^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$")

    @Test
    fun `비밀번호 null - 실패`() {
        // given
        val password = null

        // when
        val isValid = passwordCheckValidator.isValid(password, null)

        // then
        assertThat(isValid).isFalse()
    }

    @Test
    fun `비밀번호 유효성 - 실패`() {
        // given
        val password = "12345"

        // when
        val isValid = passwordCheckValidator.isValid(password, null)

        // then
        assertThat(isValid).isFalse()
    }

    @Test
    fun `비밀번호 유효성 - 성공`() {
        // given
        val password = "abcd1234!@#ABC"

        // when
        val isValid = passwordCheckValidator.isValid(password, null)

        // then
        assertThat(isValid).isTrue()
    }
}
