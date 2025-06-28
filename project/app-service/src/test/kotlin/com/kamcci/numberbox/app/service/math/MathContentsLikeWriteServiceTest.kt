package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.NOT_EXIST_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.app.service.mock.port.orm.math.MockMathContentsLikeWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

class MathContentsLikeWriteServiceTest {
    private val mathConLikeModifyPort = MockMathContentsLikeWriteOrmPort()

    private val mathContentsLikeWriteService =
        MathContentsLikeWriteService(mathConLikeModifyPort)


    @Test
    fun `좋아요 - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(NOT_EXIST_ID, UUID.randomUUID())

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.save(modifyDto)
        }
    }


    @Test
    fun `좋아요 삭제 - 성공`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(SUCCESS_ID, UUID.randomUUID())

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.delete(modifyDto)
        }
    }

}