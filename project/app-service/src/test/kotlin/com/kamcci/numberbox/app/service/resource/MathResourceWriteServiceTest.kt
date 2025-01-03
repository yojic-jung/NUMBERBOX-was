package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.resource.MathResourceWriteOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.service.resource.MathResourceFixture.getMathResourceCreateDto
import com.kamcci.numberbox.app.service.resource.MathResourceFixture.getMathResourceFileVo
import com.kamcci.numberbox.app.service.resource.MathResourceFixture.getMathResourceUpdateDtoList
import com.kamcci.numberbox.app.service.resource.MathResourceWriteService.Companion.NOT_MY_CONTENTS
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.util.*

class MathResourceWriteServiceTest {
    private val mathResourceReadOrmPort: MathResourceReadCase = mock()
    private val mathResourceWriteOrmPort: MathResourceWriteOrmPort = mock()
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort = mock()

    private val mathResourceWriteService =
        MathResourceWriteService(mathResourceReadOrmPort, mathResourceWriteOrmPort, sysGarbageFileWriteOrmPort)


    @Test
    fun `학습 자료 생성 - 성공`() {
        // given
        val createDto = getMathResourceCreateDto()

        // when
        mathResourceWriteService.create(createDto)

        // then
        verify(mathResourceWriteOrmPort).create(createDto)
    }

    @Test
    fun `학습 자료 수정 - 성공`() {
        // given
        val updateDtoList = getMathResourceUpdateDtoList()
        val resourceFileVo = getMathResourceFileVo()

        for (updateDto in updateDtoList) {
            Mockito.`when`(mathResourceReadOrmPort.readFileById(updateDto.resourceId)).thenReturn(resourceFileVo)

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

        Mockito.`when`(mathResourceWriteOrmPort.deleteByIdAndMemberId(id, memberId)).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.deleteByIdAndMemberId(id, memberId)
        }
    }

    @Test
    fun `학습 자료 삭제 - 실패`() {
        // given
        val id = 1L
        val memberId = UUID.randomUUID()

        Mockito.`when`(mathResourceWriteOrmPort.deleteByIdAndMemberId(id, memberId)).thenReturn(0L)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathResourceWriteService.deleteByIdAndMemberId(id, memberId)
        }
        assertThat(exception.msg).isEqualTo(NOT_MY_CONTENTS)
    }
}