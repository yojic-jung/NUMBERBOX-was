package com.kamcci.numberbox.app.service.math

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.NOT_EXIST_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.PERSIST_SUCCESS_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
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
    // 테스트 더블 및 대상 설정
    private val mathContentsReadOrmPort = MockMathContentsReadCase()
    private val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
    private val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

    // 테스트 데이터
    private val mathContentsModifyDto = getMathContentsModifyDto("any", listOf(""))
    private val mathConLicModifyDto = getMathConLicenseModifyDto()
    private val mathConIpsiSrcModifyDto = getMathConIpsiSrcModifyDto()

    @Test
    fun `사용자 수학문제 등록 - 성공`() {
        // given - 독립적인 MathContentsWriteService 인스턴스 생성 (동시성 제어)
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createUserCustomContents(mathContentsModifyDto, mathConLicModifyDto)

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }


    @Test
    fun `자체 수학문제 등록 - 성공`() {
        // given - 독립적인 MathContentsWriteService 인스턴스 생성 (동시성 제어)
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createInHouseContents(mathContentsModifyDto, getMathConSimilarSrcCreateDto())

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }

    @Test
    fun `변형문제 등록 - 성공`() {
        // given
        val orgContentsId = SUCCESS_ID

        // when
        val contentsId = mathContentsWriteService.createTransContents(orgContentsId, mathContentsModifyDto)

        // then
        assertThat(contentsId).isEqualTo(PERSIST_SUCCESS_ID)
    }

    @Test
    fun `변형문제 등록 - 실패(원본 문제 미존재)`() {
        // given
        val notExistId = NOT_EXIST_ID

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            mathContentsWriteService.createTransContents(notExistId, mathContentsModifyDto)
        }
        assertThat(exception.msg).isEqualTo(MathContentsWriteService.NOT_EXIST_CONTENTS)
    }

    @Test
    fun `입시 수학문제 등록 - 성공`() {
        // given - 독립적인 MathContentsWriteService 인스턴스 생성 (동시성 제어)
        val mathContentsReadOrmPort = MockMathContentsReadCase()
        val mathContentsWriteOrmPort = MockMathContentsWriteOrmPort()
        val mathContentsWriteService = MathContentsWriteService(mathContentsReadOrmPort, mathContentsWriteOrmPort)

        // when
        mathContentsWriteService.createIpsiContents(mathContentsModifyDto, mathConIpsiSrcModifyDto)

        // then
        assertThat(mathContentsWriteOrmPort.executeCnt).isOne()
    }

    @Test
    fun `사용자 수학 문제 수정 - 성공`() {
        // given
        val contentsId = SUCCESS_ID

        // when
        assertDoesNotThrow {
            mathContentsWriteService.updateUserCustomContents(
                contentsId,
                mathContentsModifyDto,
                mathConLicModifyDto
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
                mathContentsModifyDto,
                mathConLicModifyDto
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `자체제작 수학 문제 수정 - 성공`() {
        // given
        val contentsId = SUCCESS_ID

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateInHouseContents(
                contentsId,
                mathContentsModifyDto,
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
                mathContentsModifyDto,
                getMathConSimilarSrcCreateDto()
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }


    @Test
    fun `입시 문제 수정 - 성공`() {
        // given
        val contentsId = SUCCESS_ID

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateIpsiContents(
                contentsId,
                mathContentsModifyDto,
                mathConIpsiSrcModifyDto,
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
                mathContentsModifyDto,
                mathConIpsiSrcModifyDto,
            )
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `변형 문제 수정 - 성공`() {
        // given
        val contentsId = SUCCESS_ID

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.updateTransContents(contentsId, mathContentsModifyDto)
        }
    }

    @Test
    fun `변형 문제 수정 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.updateTransContents(contentsId, mathContentsModifyDto)
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `수학 문제 삭제 - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val memberId = UUID.randomUUID()

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.delete(contentsId, memberId)
        }
    }

    @Test
    fun `수학 문제 삭제 - 실패`() {
        // given
        val contentsId = FAIL_ID
        val memberId = UUID.randomUUID()

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.delete(contentsId, memberId)
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_DELETED_CONTENTS)
    }

    @Test
    fun `수학 문제 삭제(사용자 모든 문제) - 성공`() {
        // given
        val memberId = UUID.randomUUID()

        // when & then
        assertDoesNotThrow {
            mathContentsWriteService.delete(memberId)
        }
    }

    @Test
    fun `수학 문제 삭제(사용자 모든 문제) - 실패`() {
        // given
        val memberId = FAIL_MEMBER_ID

        // when & then
        val ex = assertThrows<BusinessInValidException> {
            mathContentsWriteService.delete(memberId)
        }
        assertThat(ex.msg).isEqualTo(MathContentsWriteService.NOT_DELETED_CONTENTS)
    }
}
