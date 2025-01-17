package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class MemberSignupRequestTest {
    @Test
    fun `MemberSignupRequest 정상 생성`() {
        val password = "password"
        val confirmPassword = "password"
        val req = MemberSignupRequest(
            "email",
            password,
            confirmPassword,
            UUID.randomUUID(),
        )

        // 비밀번호 및 비밀번호 확인 같은 경우 정상 생성
        assertThat(req.password).isEqualTo(req.confirmPassword)
    }

    @Test
    fun `MemberSignupRequest 생성 불가`() {
        val password = "password"
        val confirmPassword = "password123"

        assertThrows<BusinessInValidException> {
            MemberSignupRequest(
                "email",
                password,
                confirmPassword,
                UUID.randomUUID(),
            )

        }
    }
}