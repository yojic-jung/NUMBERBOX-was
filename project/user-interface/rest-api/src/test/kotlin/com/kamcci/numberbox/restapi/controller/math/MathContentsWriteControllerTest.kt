package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dto.request.math.MathContestGrammarModifyRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsDummyData.getMathConLicenseCreateRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsDummyData.getMathConLicenseUpdtRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsDummyData.getMathConTransCreateRequest
import com.kamcci.numberbox.restapi.dummy.math.MathContentsDummyData.getMathConTransUpdtRequest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathContentsWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/content"
        const val USER_CUSTOM_URL = "$PREFIX/user-custom"
        const val TRANS_CONTENTS_URL = "$PREFIX/trans"
        const val GRAMMAR_URL = "$PREFIX/grammar"
        const val DEL_URL = PREFIX
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
        val updateReq = getMathConTransUpdtRequest()

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