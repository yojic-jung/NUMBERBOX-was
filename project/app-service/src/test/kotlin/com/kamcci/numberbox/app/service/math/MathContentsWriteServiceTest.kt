package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.mock.port.orm.math.MockMathContentsWriteOrmPort
import com.kamcci.numberbox.app.service.mock.usecase.math.MockMathContentsReadCase
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConLicenseModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathContentsModifyDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MathContentsWriteServiceTest {
    private val mathContentsReadOrmPort = MockMathContentsReadCase()
    private val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
    private val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

    @Test
    fun `사용자 수학문제 등록 - 성공`() {
        // given
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createUserCustomContents(getMathContentsModifyDto(), getMathConLicenseModifyDto())

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }

    @Test
    fun `자체 수학문제 등록 - 성공`() {
        // given
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createInHouseContents(getMathContentsModifyDto(), getMathConSimilarSrcCreateDto())

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }

    @Test
    fun `변형문제 등록 - 성공`() {
        // given
        val orgContentsId = 1L

        // when
        val contentsId = mathContentsWriteService.createTransContents(orgContentsId, getMathContentsModifyDto())

        // then
        assertThat(contentsId).isEqualTo(1L)
    }

    @Test
    fun `변형문제 등록 - 실패(원본 문제 미존재)`() {
        // given
        val notExistId = EXIST_ID + 1L

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsWriteService.createTransContents(notExistId, getMathContentsModifyDto())
        }
        assertThat(exception.msg).isEqualTo(MathContentsWriteService.NOT_EXIST_CONTENTS)
    }

    @Test
    fun `입시 수학문제 등록 - 성공`() {
        // given
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createIpsiContents(getMathContentsModifyDto(), getMathConIpsiSrcModifyDto())

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }

    @Test
    fun `사용자 수학 문제 수정 - 성공`() {
        // given
        val contentsId = 1L

        // when
        assertDoesNotThrow {
            mathContentsWriteService.updateUserCustomContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConLicenseModifyDto()
            )
        }
    }

    @Test
    fun `사용자 수학 문제 수정 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateUserCustomContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConLicenseModifyDto()
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `자체제작 수학 문제 수정 - 성공`() {
        // given
        val contentsId = 1L

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateInHouseContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConSimilarSrcCreateDto()
            )
        }
    }

    @Test
    fun `자체제작 수학 문제 수정 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateInHouseContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConSimilarSrcCreateDto()
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }


    @Test
    fun `입시 문제 수정 - 성공`() {
        // given
        val contentsId = 1L

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateIpsiContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConIpsiSrcModifyDto(),
            )
        }
    }

    @Test
    fun `입시 문제 수정 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateIpsiContents(
                contentsId,
                getMathContentsModifyDto(),
                getMathConIpsiSrcModifyDto(),
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `변형 문제 수정 - 성공`() {
        // given
        val contentsId = 1L

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateTransContents(
                contentsId,
                getMathContentsModifyDto(),
            )
        }
    }

    @Test
    fun `변형 문제 수정 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateTransContents(
                contentsId,
                getMathContentsModifyDto(),
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `수학 문제 삭제 - 성공`() {
        // given
        val contentsId = 1L
        val memberId = UUID.randomUUID()

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
        val contentsId = FAIL_ID
        val memberId = UUID.randomUUID()

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
        val memberId = FAIL_MEMBER_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.delete(
                memberId,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_DELETED_CONTENTS)
    }
}
