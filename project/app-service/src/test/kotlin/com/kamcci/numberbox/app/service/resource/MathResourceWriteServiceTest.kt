package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceCreateDto
import com.kamcci.numberbox.app.service.dummy.MathResourceDummyData.getMathResourceUpdateDtoList
import com.kamcci.numberbox.app.service.resource.MathResourceWriteService.Companion.NOT_MY_CONTENTS
import com.kamcci.numberbox.app.service.stub.port.orm.resource.MockMathResourceWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.sys.MockSysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.service.stub.usecase.resource.MockMathResourceReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MathResourceWriteServiceTest {
    private val mathResourceReadOrmPort = MockMathResourceReadCase()
    private val mathResourceWriteOrmPort = MockMathResourceWriteOrmPort()
    private val sysGarbageFileWriteOrmPort = MockSysGarbageFileWriteOrmPort()

    private val mathResourceWriteService =
        MathResourceWriteService(mathResourceReadOrmPort, mathResourceWriteOrmPort, sysGarbageFileWriteOrmPort)


    @Test
    fun `학습 자료 생성 - 성공`() {
        // given
        val createDto = getMathResourceCreateDto()

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.create(createDto)
        }
    }

    @Test
    fun `학습 자료 수정 - 성공`() {
        // given
        val updateDtoList = getMathResourceUpdateDtoList()

        for (updateDto in updateDtoList) {
            // when & then
            assertDoesNotThrow {
                mathResourceWriteService.update(updateDto)
            }
        }
    }

    @Test
    fun `학습 자료 삭제 - 성공`() {
        // given
        val id = 1L
        val memberId = UUID.randomUUID()

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.deleteByIdAndMemberId(id, memberId)
        }
    }

    @Test
    fun `학습 자료 삭제 - 실패`() {
        // given
        val id = FAIL_ID
        val memberId = UUID.randomUUID()

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathResourceWriteService.deleteByIdAndMemberId(id, memberId)
        }
        assertThat(exception.msg).isEqualTo(NOT_MY_CONTENTS)
    }
}