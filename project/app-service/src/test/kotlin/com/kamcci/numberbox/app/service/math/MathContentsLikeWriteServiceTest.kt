package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeReadOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsLikeWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class MathContentsLikeWriteServiceTest {
    private val mathConLikeReadOrmPort: MathContentsLikeReadOrmPort = mock()
    private val mathConLikeModifyPort: MathContentsLikeWriteOrmPort = mock()

    private val mathContentsLikeWriteService =
        MathContentsLikeWriteService(mathConLikeReadOrmPort, mathConLikeModifyPort)


    private val modifyDto = MathContentsLikeModifyDto(1L, UUID.randomUUID())

    @Test
    fun `좋아요 - 성공`() {
        // given
        Mockito.`when`(mathConLikeReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId))
            .thenReturn(false)

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.save(modifyDto)
        }
    }

    @Test
    fun `좋아요 - 실패(수학문제 미존재)`() {
        // given
        Mockito.`when`(mathConLikeReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId))
            .thenReturn(true)

        // when & then
        assertThrows<BusinessValidException> {
            mathContentsLikeWriteService.save(modifyDto)
        }
    }

    @Test
    fun `좋아요 - 삭제`() {
        // given
        Mockito.`when`(mathConLikeReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId))
            .thenReturn(true)

        // when & then
        assertDoesNotThrow {
            mathContentsLikeWriteService.delete(modifyDto)
        }
    }

    @Test
    fun `좋아요 - 삭제(수학문제 미존재)`() {
        // given
        Mockito.`when`(mathConLikeReadOrmPort.existByContentsIdAndMemberId(modifyDto.contentsId, modifyDto.memberId))
            .thenReturn(false)

        // when & then
        assertThrows<BusinessValidException> {
            mathContentsLikeWriteService.delete(modifyDto)
        }
    }
}