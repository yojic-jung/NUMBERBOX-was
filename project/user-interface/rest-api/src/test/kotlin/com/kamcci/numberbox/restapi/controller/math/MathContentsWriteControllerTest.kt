package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.controller.math.MathContentsWriteController.Companion.NOT_UPDATED_CONTENTS
import com.kamcci.numberbox.restapi.dto.request.math.MathContestGrammarModifyRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConLicenseCreateRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConLicenseUpdtRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConTransCreateRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MathContentsWriteControllerTest @Autowired constructor(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort,
    private val mathContentsWriteCase: MathContentsWriteCase,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/content"
        const val USER_CUSTOM_URL = "$PREFIX/user-custom"
        const val TRANS_CONTENTS_URL = "$PREFIX/trans"
        const val GRAMMAR_URL = "$PREFIX/grammar"
        const val DEL_URL = PREFIX
    }

    @BeforeEach
    fun setUp() {
        Mockito.reset(mathContentsWriteCase) // Mock 상태 리셋
    }

    @Test
    fun `사용자 제작 문제 등록 - 성공`() {
        // given
        val createReq = getMathConLicenseCreateRequest()

        // when
        val resultAction = postRequest(USER_CUSTOM_URL, createReq)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 제작 문제 수정 - 성공`() {
        // given
        val updateReq = getMathConLicenseUpdtRequest()
        Mockito.`when`(mathContentsReadOrmPort.existById(any())).thenReturn(true)
        Mockito.`when`(mathContentsWriteCase.updateUserCustomContents(anyOrNull(), anyOrNull(), anyOrNull()))
            .thenReturn(true)

        // when
        val resultAction = putRequest(USER_CUSTOM_URL, updateReq)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 제작 문제 수정 - 실패`() {
        // given
        val updateReq = getMathConLicenseUpdtRequest()
        Mockito.`when`(mathContentsReadOrmPort.existById(any())).thenReturn(true)
        Mockito.`when`(mathContentsWriteCase.updateUserCustomContents(anyOrNull(), anyOrNull(), anyOrNull()))
            .thenReturn(false)

        // when
        val resultAction = putRequest(USER_CUSTOM_URL, updateReq)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessValidException::class)
        assertExMsg(resultAction, NOT_UPDATED_CONTENTS)
    }

    @Test
    fun `변형문제 등록 - 성공`() {
        // given
        val createReq = getMathConTransCreateRequest()

        // when
        val resultAction = postRequest(TRANS_CONTENTS_URL, createReq)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `변형문제 수정 - 성공`() {
        // given
        val updateReq = getMathConTransCreateRequest()
        Mockito.`when`(mathContentsReadOrmPort.existById(any())).thenReturn(true)
        Mockito.`when`(mathContentsWriteCase.updateTransContents(anyOrNull(), anyOrNull()))
            .thenReturn(false)

        // when
        val resultAction = putRequest(TRANS_CONTENTS_URL, updateReq)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 문법 등록 - 성공`() {
        // given
        val req = MathContestGrammarModifyRequest(1L, "asdf")

        // when
        val resultAction = postRequest(GRAMMAR_URL, req)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 삭제 - 성공`() {
        // given
        val contentsId = 1

        // when
        val resultAction = delRequest("$DEL_URL/$contentsId")

        // then
        assert2xx(resultAction)
    }
}