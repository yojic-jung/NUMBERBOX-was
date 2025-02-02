package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.stub.common.MockUserDetailArgumentResolver.Companion.EMAIL_FROM_RESOLVER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MemberReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/member"
        const val EMAIl_URL = "$PREFIX/email"
    }

    @Test
    fun `이메일 조회 - 성공`() {
        // when
        val resultAction = getRequest(EMAIl_URL)

        // then
        assertThat(removeQuotes(takeJsonResponseData(resultAction).get("email"))).isEqualTo(EMAIL_FROM_RESOLVER)
    }
}