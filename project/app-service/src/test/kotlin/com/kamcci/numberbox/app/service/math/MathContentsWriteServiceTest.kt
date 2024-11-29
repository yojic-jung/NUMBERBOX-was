package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class MathContentsWriteServiceTest {
    private val mathContentsReadOrmPort: MathContentsReadOrmPort = mock()
    private val mathContentsWriteOrmPort: MathContentsWriteOrmPort = mock()

    private val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

    private val orgContentsId = 1L
    private val returnValue = 10L
    private val svcPosbSttsType = ContentsSvcPosbSttsType.Release
    private val mathContentsModifyDto = MathContentsModifyDto(
        UUID.randomUUID(),
        1,
        1,
        "",
        "",
        "",
        listOf(""),
        "",
        "",
        "",
        "",
        "",
        1
    )

    @Test
    fun `변형문제 등록 - 성공`() {
        // given
        Mockito.`when`(mathContentsReadOrmPort.existById(orgContentsId)).thenReturn(true)
        Mockito.`when`(mathContentsReadOrmPort.readTransContCntById(orgContentsId)).thenReturn(1)
        Mockito.`when`(
            mathContentsWriteOrmPort.saveTransContents(orgContentsId, svcPosbSttsType, mathContentsModifyDto)
        ).thenReturn(returnValue)

        // when
        val contentsId = mathContentsWriteService.createTransContents(orgContentsId, mathContentsModifyDto)

        // then
        assertThat(contentsId).isEqualTo(returnValue)
    }

    @Test
    fun `변형문제 등록 - 실패(원본 문제 미존재)`() {
        // given
        Mockito.`when`(mathContentsReadOrmPort.existById(orgContentsId)).thenReturn(false)

        // when & then
        val exception = assertThrows<BusinessValidException> {
            mathContentsWriteService.createTransContents(orgContentsId, mathContentsModifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsWriteService.NOT_EXIST_CONTENTS)
    }
}
