package com.kamcci.numberbox.restapi.util.auth

import com.kamcci.numberbox.restapi.stub.auth.MockAuthPasswordEncoder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AuthPasswordEncoderWrapperTest {

    @Test
    fun `matches 동작 - 성공`() {
        // given
        val authPasswordEncoder = MockAuthPasswordEncoder()
        val authPasswordEncoderWrapper = AuthPasswordEncoderWrapper(authPasswordEncoder)

        // when
        authPasswordEncoderWrapper.matches("", "")

        // then
        assertThat(authPasswordEncoder.excuteCnt).isEqualTo(1)
    }

    @Test
    fun `encode 동작 - 성공`() {
        // given
        val authPasswordEncoder = MockAuthPasswordEncoder()
        val authPasswordEncoderWrapper = AuthPasswordEncoderWrapper(authPasswordEncoder)

        // when
        authPasswordEncoderWrapper.encode("")

        // then
        assertThat(authPasswordEncoder.excuteCnt).isEqualTo(1)
    }

}