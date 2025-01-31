package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConLicenseModifyDto
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathContentsModifyDto
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.util.*

class MathContentsWriteServiceTest {
    private val mathContentsReadOrmPort: MathContentsReadCase = mock()
    private val mathContentsWriteOrmPort: MathContentsWriteOrmPort = mock()

    private val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

    private val orgContentsId = 1L
    private val returnValue = 10L
    private val svcPosbSttsType = ContentsSvcPosbSttsType.Release
    private val mathContentsModifyDto = getMathContentsModifyDto()

    @Test
    fun `사용자 수학문제 등록 - 성공`() {
        // given
        val contentsModifyDto = getMathContentsModifyDto()
        val licenseDto = getMathConLicenseModifyDto()

        // when
        mathContentsWriteService.createUserCustomContents(contentsModifyDto, licenseDto)

        // then
        verify(mathContentsWriteOrmPort).saveWithLicense(
            ContentsSvcPosbSttsType.Release,
            contentsModifyDto,
            licenseDto
        )
    }

    @Test
    fun `자체 수학문제 등록 - 성공`() {
        // given
        val contentsModifyDto = getMathContentsModifyDto()
        val similarSrcDto = getMathConSimilarSrcCreateDto()

        // when
        mathContentsWriteService.createInHouseContents(contentsModifyDto, similarSrcDto)

        // then
        verify(mathContentsWriteOrmPort).saveWithSimilarSrc(
            ContentsSvcPosbSttsType.NotRelease,
            contentsModifyDto,
            similarSrcDto
        )
    }

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
        val exception = assertThrows<BusinessInValidException> {
            mathContentsWriteService.createTransContents(orgContentsId, mathContentsModifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsWriteService.NOT_EXIST_CONTENTS)
    }

    @Test
    fun `입시 수학문제 등록 - 성공`() {
        // given
        val contentsModifyDto = getMathContentsModifyDto()
        val ipsiSrcCreateDto = getMathConIpsiSrcModifyDto()

        // when
        mathContentsWriteService.createIpsiContents(contentsModifyDto, ipsiSrcCreateDto)

        // then
        verify(mathContentsWriteOrmPort).saveWithIpsiSrc(
            ContentsSvcPosbSttsType.Release,
            contentsModifyDto,
            ipsiSrcCreateDto
        )
    }

    @Test
    fun `사용자 수학 문제 수정 - 성공`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val licenseCreateDto = getMathConLicenseModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithLicense(
                contentsId,
                ContentsSvcPosbSttsType.Release, contentsModifyDto, licenseCreateDto
            )
        ).thenReturn(1L)

        // when
        assertDoesNotThrow {
            mathContentsWriteService.updateUserCustomContents(contentsId, contentsModifyDto, licenseCreateDto)
        }
    }

    @Test
    fun `사용자 수학 문제 수정 - 실패`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val licenseCreateDto = getMathConLicenseModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithLicense(
                contentsId,
                ContentsSvcPosbSttsType.Release, contentsModifyDto, licenseCreateDto
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateUserCustomContents(contentsId, contentsModifyDto, licenseCreateDto)
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `자체제작 수학 문제 수정 - 성공`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val similarSrcDto = getMathConSimilarSrcCreateDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithSimilarSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                contentsModifyDto,
                similarSrcDto
            )
        ).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateInHouseContents(
                contentsId,
                contentsModifyDto,
                similarSrcDto
            )
        }
    }

    @Test
    fun `자체제작 수학 문제 수정 - 실패`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val similarSrcDto = getMathConSimilarSrcCreateDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithSimilarSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release, contentsModifyDto, similarSrcDto
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateInHouseContents(
                contentsId,
                contentsModifyDto,
                similarSrcDto
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }


    @Test
    fun `입시 문제 수정 - 성공`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val ipsiSrcCreateDto = getMathConIpsiSrcModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithIpsiSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                contentsModifyDto,
                ipsiSrcCreateDto,
            )
        ).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateIpsiContents(
                contentsId,
                contentsModifyDto,
                ipsiSrcCreateDto,
            )
        }
    }

    @Test
    fun `입시 문제 수정 - 실패`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()
        val ipsiSrcCreateDto = getMathConIpsiSrcModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateWithIpsiSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                contentsModifyDto,
                ipsiSrcCreateDto,
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateIpsiContents(
                contentsId,
                contentsModifyDto,
                ipsiSrcCreateDto,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `변형 문제 수정 - 성공`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateTransContents(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                contentsModifyDto,
            )
        ).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateTransContents(
                contentsId,
                contentsModifyDto,
            )
        }
    }

    @Test
    fun `변형 문제 수정 - 실패`() {
        // given
        val contentsId = 1L
        val contentsModifyDto = getMathContentsModifyDto()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateTransContents(
                contentsId,
                ContentsSvcPosbSttsType.Release, contentsModifyDto,
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateTransContents(
                contentsId,
                contentsModifyDto,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `수학 문제 삭제 - 성공`() {
        // given
        val contentsId = 1L
        val memberId = UUID.randomUUID()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateContentsClassifyType(
                contentsId,
                memberId,
                ContentsClassifyType.Deleted,
            )
        ).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.delete(
                contentsId,
                memberId,
            )
        }
    }

    @Test
    fun `수학 문제 삭제 - 실패`() {
        // given
        val contentsId = 1L
        val memberId = UUID.randomUUID()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateContentsClassifyType(
                contentsId,
                memberId,
                ContentsClassifyType.Deleted,
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.delete(
                contentsId,
                memberId,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_DELETED_CONTENTS)
    }

    @Test
    fun `수학 문제 삭제(사용자 모든 문제) - 성공`() {
        // given
        val memberId = UUID.randomUUID()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateContentsClassifyType(
                memberId,
                ContentsClassifyType.Deleted,
            )
        ).thenReturn(1L)

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.delete(
                memberId,
            )
        }
    }

    @Test
    fun `수학 문제 삭제(사용자 모든 문제) - 실패`() {
        // given
        val memberId = UUID.randomUUID()

        Mockito.`when`(
            mathContentsWriteOrmPort.updateContentsClassifyType(
                memberId,
                ContentsClassifyType.Deleted,
            )
        ).thenReturn(0L)

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.delete(
                memberId,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_DELETED_CONTENTS)
    }
}
