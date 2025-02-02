package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.service.stub.port.orm.math.MockMathContentsLikeWriteOrmPort
import com.kamcci.numberbox.app.service.stub.usecase.math.MockMathContentsLikeReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MathContentsLikeWriteServiceTest {
    private val mathConLikeReadCase = MockMathContentsLikeReadCase()
    private val mathConLikeModifyPort = MockMathContentsLikeWriteOrmPort()

    private val mathContentsLikeWriteService =
        MathContentsLikeWriteService(mathConLikeReadCase, mathConLikeModifyPort)


    @Test
    fun `좋아요 - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(EXIST_ID + 1L, UUID.randomUUID())

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.save(modifyDto)
        }
    }

    @Test
    fun `좋아요 - 실패(이미 좋아요 누름)`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(EXIST_ID, UUID.randomUUID())

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsLikeWriteService.save(modifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsLikeWriteService.ALREADY_EXIST)
    }

    @Test
    fun `좋아요 삭제 - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(1L, UUID.randomUUID())

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.delete(modifyDto)
        }
    }

    @Test
    fun `좋아요 삭제(좋아요 누른적 없음) - 실패`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(EXIST_ID + 1L, UUID.randomUUID())

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsLikeWriteService.delete(modifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsLikeWriteService.NOT_EXIST)
    }
}