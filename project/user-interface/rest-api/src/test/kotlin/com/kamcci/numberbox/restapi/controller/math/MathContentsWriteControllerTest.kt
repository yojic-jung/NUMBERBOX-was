package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dto.request.math.MathContestGrammarModifyRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConLicenseCreateRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConLicenseUpdtRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathConTransCreateRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MathContentsWriteControllerTest @Autowired constructor(
    private val mathContentsReadCase: MathContentsReadCase,
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
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(true)

        // when
        val resultAction = putRequest(USER_CUSTOM_URL, updateReq)

        // then
        assert2xx(resultAction)
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
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(true)

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