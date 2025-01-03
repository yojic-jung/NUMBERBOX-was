package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageWriteOrmPort
import com.kamcci.numberbox.app.service.docs.MathDocsFixture.getMathDocsUsageCreateDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class MathDocsUsageWriteServiceTest {
    private val mathDocsUsageWriteOrmPort: MathDocsUsageWriteOrmPort = mock()
    private val mathDocsUsageWriteService = MathDocsUsageWriteService(mathDocsUsageWriteOrmPort)

    @Test
    fun `학습지 사용 로그 저장 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsUsageCreateDto()
        val actualId = 1L

        Mockito.`when`(mathDocsUsageWriteOrmPort.create(memberId, createDto)).thenReturn(actualId)

        // when
        val expectedId = mathDocsUsageWriteService.create(memberId, createDto)

        // then
        assertThat(expectedId).isEqualTo(actualId)
    }

    @Test
    fun `학습지 사용 로그 저장 - 실패`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsUsageCreateDto()
        val actualId = 0L

        Mockito.`when`(mathDocsUsageWriteOrmPort.create(memberId, createDto)).thenReturn(actualId)

        // when
        val ex = assertThrows<BusinessInValidException> {
            mathDocsUsageWriteService.create(memberId, createDto)
        }
        assertThat(ex.msg).isEqualTo(MathDocsUsageWriteService.NOT_SAVED)

    }
}