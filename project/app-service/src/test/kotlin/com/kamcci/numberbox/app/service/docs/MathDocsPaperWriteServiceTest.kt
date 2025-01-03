package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.service.docs.MathDocsFixture.getMathDocsPaperCreateDto
import com.kamcci.numberbox.app.service.docs.MathDocsFixture.getMathDocsPaperUpdtDto
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.util.*

class MathDocsPaperWriteServiceTest {
    private val mathDocsPaperWriteOrmPort: MathDocsPaperWriteOrmPort = mock()
    private val mathDocsPaperWriteService = MathDocsPaperWriteService(mathDocsPaperWriteOrmPort)

    @Test
    fun `학습지 생성 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsPaperCreateDto()

        Mockito.`when`(mathDocsPaperWriteOrmPort.create(memberId, createDto)).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.create(memberId, createDto)
        }
    }

    @Test
    fun `학습지 생성 - 실패`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsPaperCreateDto()

        Mockito.`when`(mathDocsPaperWriteOrmPort.create(memberId, createDto)).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathDocsPaperWriteService.create(memberId, createDto)
        }
        assertThat(ex.msg).isEqualTo(MathDocsPaperWriteService.NOT_SAVED)
    }


    @Test
    fun `학습지 수정 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val updtDto = getMathDocsPaperUpdtDto()

        Mockito.`when`(mathDocsPaperWriteOrmPort.update(memberId, updtDto)).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.update(memberId, updtDto)
        }
    }

    @Test
    fun `학습지 수정 - 실패`() {
        // given
        val memberId = UUID.randomUUID()
        val updtDto = getMathDocsPaperUpdtDto()

        Mockito.`when`(mathDocsPaperWriteOrmPort.update(memberId, updtDto)).thenReturn(0L)

        // when
        val ex = assertThrows<BusinessInValidException> {
            mathDocsPaperWriteService.update(memberId, updtDto)
        }
        assertThat(ex.msg).isEqualTo(MathDocsPaperWriteService.NOT_MY_DOCS)
    }

    @Test
    fun `학습지 삭제 - 성공`() {
        // given
        val docsId = 1L
        val memberId = UUID.randomUUID()

        Mockito.`when`(mathDocsPaperWriteOrmPort.delete(docsId, memberId)).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.delete(docsId, memberId)
        }
    }

    @Test
    fun `학습지 삭제 - 실패`() {
        // given
        val docsId = 1L
        val memberId = UUID.randomUUID()

        Mockito.`when`(mathDocsPaperWriteOrmPort.delete(docsId, memberId)).thenReturn(0L)

        // when
        val ex = assertThrows<BusinessInValidException> {
            mathDocsPaperWriteService.delete(docsId, memberId)
        }
        assertThat(ex.msg).isEqualTo(MathDocsPaperWriteService.NOT_MY_DOCS)
    }
}