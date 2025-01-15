package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathContentsModifyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MathContentsModifyRequestTest {
    @Test
    fun `미존재 객관식 번호로 생성`() {
        assertThrows<IllegalArgumentException> {
            getMathContentsModifyRequest(listOf("0"), 1)
        }
    }

    @Test
    fun `주관식으로 생성`() {
        assertDoesNotThrow {
            getMathContentsModifyRequest(null, 1)
        }
    }

    @Test
    fun `미존재 문제 난이도로 생성`() {
        assertThrows<IllegalArgumentException> {
            getMathContentsModifyRequest(listOf(), 0)
        }
    }
}