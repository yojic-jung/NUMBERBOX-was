package com.kamcci.numberbox.restapi.util.auth

import com.kamcci.modules.auth.control.util.AuthPasswordEncoder
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class AuthPasswordEncoderWrapperTest {
    private val authPasswordEncoder: AuthPasswordEncoder = mock()
    private val authPasswordEncoderWrapper = AuthPasswordEncoderWrapper(authPasswordEncoder)

    @Test
    fun `matches 동작 - 성공`() {
        authPasswordEncoderWrapper.matches("", "")

        // then
        verify(authPasswordEncoder).matches("", "")
    }

    @Test
    fun `encode 동작 - 성공`() {
        authPasswordEncoderWrapper.encode("")

        // then
        verify(authPasswordEncoder).encode("")
    }

}