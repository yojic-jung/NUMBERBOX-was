package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceFileVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceImgVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceWriteOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MathResourceWriteServiceTest {
    private val mathResourceReadOrmPort: MathResourceReadOrmPort = mock()
    private val mathResourceWriteOrmPort: MathResourceWriteOrmPort = mock()
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort = mock()

    private val mathResourceWriteService =
        MathResourceWriteService(mathResourceReadOrmPort, mathResourceWriteOrmPort, sysGarbageFileWriteOrmPort)

    private val resourceFileVo = MathResourceFileVo(
        1L,
        "",
        "",
        "",
        "",
        listOf(MathResourceImgVo("", ""))
    )

    @Test
    fun `학습 자료 수정 - 성공(ppt, 이미지 존재)`() {
        // given
        val updateDto = MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "",
            pptFilePath = "",
            pptPageCnt = 5,
            imgPath = "",
            imgName = "",
            cateList = listOf(""),
            imgList = listOf(FileNameVo("", "")),
        )
        Mockito.`when`(mathResourceReadOrmPort.readFileById(updateDto.resourceId)).thenReturn(resourceFileVo)

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.update(updateDto)
        }
    }

    @Test
    fun `학습 자료 수정 - 성공(ppt, 이미지 미존재)`() {
        // given
        val updateDto = MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = null,
            pptFilePath = null,
            pptPageCnt = null,
            imgPath = null,
            imgName = null,
            cateList = listOf(""),
            imgList = listOf(),
        )
        Mockito.`when`(mathResourceReadOrmPort.readFileById(updateDto.resourceId)).thenReturn(resourceFileVo)

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.update(updateDto)
        }
    }

    @Test
    fun `학습 자료 수정 - 성공(ppt 존재)`() {
        // given
        val updateDto = MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = "",
            pptFilePath = "",
            pptPageCnt = 5,
            imgPath = null,
            imgName = null,
            cateList = listOf(""),
            imgList = listOf(FileNameVo("", "")),
        )
        Mockito.`when`(mathResourceReadOrmPort.readFileById(updateDto.resourceId)).thenReturn(resourceFileVo)

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.update(updateDto)
        }
    }

    @Test
    fun `학습 자료 수정 - 성공(이미지 존재)`() {
        // given
        val updateDto = MathResourceUpdateDto(
            resourceId = 1L,
            title = "",
            pptFileName = null,
            pptFilePath = null,
            pptPageCnt = null,
            imgPath = "",
            imgName = "",
            cateList = listOf(""),
            imgList = listOf(FileNameVo("", "")),
        )
        Mockito.`when`(mathResourceReadOrmPort.readFileById(updateDto.resourceId)).thenReturn(resourceFileVo)

        // when & then
        assertDoesNotThrow {
            mathResourceWriteService.update(updateDto)
        }
    }
}