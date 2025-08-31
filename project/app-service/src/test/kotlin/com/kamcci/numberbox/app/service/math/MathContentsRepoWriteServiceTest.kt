package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.port.orm.math.MathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.NOT_EXIST_ID
import com.kamcci.numberbox.app.service.mock.port.orm.math.MockMathContentsRepoWriteOrmPort
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathContentsRepoModifyDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class MathContentsRepoWriteServiceTest {
    private val mathConRepoModifyOrmPort: MathContentsRepoWriteOrmPort = MockMathContentsRepoWriteOrmPort()

    private val mathContentsRepoWriteService = MathContentsRepoWriteService(mathConRepoModifyOrmPort)

    @Test
    fun `문제 저장소 저장 - 성공`() {
        // given
        val modifyDto = getMathContentsRepoModifyDto(NOT_EXIST_ID)

        // when & then
        assertDoesNotThrow {
            mathContentsRepoWriteService.save(modifyDto)
        }
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
}