package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsUsageCreateDto
import com.kamcci.numberbox.app.service.stub.port.orm.docs.MockMathDocsUsageWriteOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class MathDocsUsageWriteServiceTest {
    private val mathDocsUsageWriteService = MathDocsUsageWriteService(MockMathDocsUsageWriteOrmPort())

    @Test
    fun `학습지 사용 로그 저장 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val createDto = getMathDocsUsageCreateDto()

        // when
        val expectedId = mathDocsUsageWriteService.create(memberId, createDto)

        // then
        assertThat(expectedId).isEqualTo(1L)
    }

    @Test
    fun `학습지 사용 로그 저장 - 실패`() {
        // given
        val memberId = FAIL_MEMBER_ID
        val createDto = getMathDocsUsageCreateDto()

        // when
        val ex = assertThrows<BusinessInValidException> {
            mathDocsUsageWriteService.create(memberId, createDto)
        }
        assertThat(ex.msg).isEqualTo(MathDocsUsageWriteService.NOT_SAVED)

    }
}