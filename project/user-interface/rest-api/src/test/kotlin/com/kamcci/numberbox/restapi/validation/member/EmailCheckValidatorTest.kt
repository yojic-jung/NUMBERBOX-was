package com.kamcci.numberbox.restapi.validation.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class EmailCheckValidatorTest {
    private val emailCheckValidator =
        EmailCheckValidator("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$")

    @Test
    fun `이메일 null - 실패`() {
        // given
        val password = null

        // when
        val isValid = emailCheckValidator.isValid(password, null)

        // then
        Assertions.assertThat(isValid).isFalse()
    }

    @Test
    fun `이메일 유효성 - 실패`() {
        // given
        val password = "12345"

        // when
        val isValid = emailCheckValidator.isValid(password, null)

        // then
        Assertions.assertThat(isValid).isFalse()
    }

    @Test
    fun `이메일 유효성 - 성공`() {
        // given
        val password = "adsd@email.com"

        // when
        val isValid = emailCheckValidator.isValid(password, null)

        // then
        Assertions.assertThat(isValid).isTrue()
    }

}