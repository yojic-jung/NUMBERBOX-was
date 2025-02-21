package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.mock.port.orm.docs.MockMathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.service.sample.MathDocsSampleData.getMathDocsPaperCreateDto
import com.kamcci.numberbox.app.service.sample.MathDocsSampleData.getMathDocsPaperUpdtDto
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MathDocsPaperWriteServiceTest {
    private val mathDocsPaperWriteOrmPort = MockMathDocsPaperWriteOrmPort()
    private val mathDocsPaperWriteService = MathDocsPaperWriteService(mathDocsPaperWriteOrmPort)

    @Test
    fun `학습지 생성 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsPaperCreateDto()

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.create(memberId, createDto)
        }
    }

    @Test
    fun `학습지 생성 - 실패`() {
        // given
        val memberId = FAIL_MEMBER_ID
        val createDto = getMathDocsPaperCreateDto()

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

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.update(memberId, updtDto)
        }
    }

    @Test
    fun `학습지 수정 - 실패`() {
        // given
        val memberId = FAIL_MEMBER_ID
        val updtDto = getMathDocsPaperUpdtDto()

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

        // when & then
        assertDoesNotThrow {
            mathDocsPaperWriteService.delete(docsId, memberId)
        }
    }

    @Test
    fun `학습지 삭제 - 실패`() {
        // given
        val docsId = FAIL_ID
        val memberId = FAIL_MEMBER_ID

        // when
        val ex = assertThrows<BusinessInValidException> {
            mathDocsPaperWriteService.delete(docsId, memberId)
        }
        assertThat(ex.msg).isEqualTo(MathDocsPaperWriteService.NOT_MY_DOCS)
    }
}