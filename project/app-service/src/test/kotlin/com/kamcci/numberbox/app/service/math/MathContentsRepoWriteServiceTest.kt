package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsRepoModifyDto
import com.kamcci.numberbox.app.service.stub.port.orm.math.MockMathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.service.stub.usecase.math.MockMathContentsRepoReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MathContentsRepoWriteServiceTest {
    private val mathConRepoReadCase: MathContentsRepoReadCase = MockMathContentsRepoReadCase()
    private val mathConRepoModifyOrmPort: MathContentsRepoWriteOrmPort = MockMathContentsRepoWriteOrmPort()

    private val mathContentsRepoWriteService = MathContentsRepoWriteService(
        mathConRepoReadCase,
        mathConRepoModifyOrmPort
    )

    @Test
    fun `문제 저장소 저장 - 성공`() {
        // given
        val modifyDto = getMathContentsRepoModifyDto(EXIST_ID + 1L)

        // when & then
        assertDoesNotThrow {
            mathContentsRepoWriteService.save(modifyDto)
        }
    }

    @Test
    fun `문제 저장소 저장 - 실패`() {
        // given
        val modifyDto = getMathContentsRepoModifyDto(EXIST_ID)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsRepoWriteService.save(modifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsRepoWriteService.ALREADY_EXIST)
    }

    @Test
    fun `문제 저장소 제거 - 성공`() {
        // given
        val modifyDto = getMathContentsRepoModifyDto(EXIST_ID)

        // when & then
        assertDoesNotThrow {
            mathContentsRepoWriteService.delete(modifyDto)
        }
    }

    @Test
    fun `문제 저장소 제거 - 실패`() {
        // given
        val modifyDto = getMathContentsRepoModifyDto(EXIST_ID + 1L)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsRepoWriteService.delete(modifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsRepoWriteService.NOT_EXIST)
    }
}