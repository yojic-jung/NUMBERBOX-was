package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.restapi.sample.math.MathContentsSampleData.getMathContentsModifyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MathContentsModifyRequestTest {
    @Test
    fun `객관식 번호로 생성`() {
        assertDoesNotThrow {
            getMathContentsModifyRequest(listOf("①", "②", "③", "④", "⑤"), 1)
        }
    }

    @Test
    fun `미존재 객관식 번호로 생성`() {
        assertThrows<IllegalArgumentException> {
            getMathContentsModifyRequest(listOf("0"), 1)
        }
    }

    @Test
    fun `주관식으로 생성`() {
        assertDoesNotThrow {
            MathContentsModifyRequest(
                unitId = 21001,
                typeId = 1,
                contents = "",
                solution = "",
                answer = "",
                choiceAnswer = null,
                firNo = "1",
                secNo = "2",
                thrNo = "3",
                fourNo = "4",
                fifNo = "5",
                quesLevel = 1
            )
        }
    }

    @Test
    fun `제공하는 문제 난이도로 생성`() {
        for (i in 1..5) {
            assertDoesNotThrow {
                getMathContentsModifyRequest(listOf(), i)
            }
        }
    }

    @Test
    fun `미존재 문제 난이도로 생성`() {
        for (i in listOf(0, 6)) {
            assertThrows<IllegalArgumentException> {
                getMathContentsModifyRequest(listOf(), i)
            }
        }
    }
}