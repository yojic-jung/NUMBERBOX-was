package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathContentsReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/content"
        const val CONTENTS_URL = PREFIX
        const val MY_CONTENTS_URL = "$PREFIX/my"
        const val USER_CONTENTS_URL = "$PREFIX/user"
        const val LIST_CONTENTS_URL = "$PREFIX/list"
        const val REPO_CONTENTS_URL = "$PREFIX/repo"
    }

    @Test
    fun `문제 id로 조회 - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val reqBody = mapOf(
            "contentsOnly" to "true",
            "contentsClassify" to "InHouse"
        )

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회 - 실패`() {
        // given
        val contentsId = FAIL_ID
        val reqBody = mapOf(
            "contentsOnly" to "true",
            "contentsClassify" to "InHouse"
        )

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
        assertExMsg(resultAction, MathContentsReadController.NOT_EXIST_CONTENTS)
    }

    @Test
    fun `문제 id로 조회(자체제작 문제) - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val reqBody = mapOf(
            "contentsOnly" to "false",
            "contentsClassify" to "InHouse"
        )

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회(입시 문제) - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val reqBody = mapOf(
            "contentsClassify" to "Ipsi"
        )
        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회(사용자 제작 문제) - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val reqBody = mapOf(
            "contentsOnly" to "false",
            "contentsClassify" to "UserCustom"
        )

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 문제 - 성공`() {
        // when
        val resultAction = getRequest(MY_CONTENTS_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 문제(페이지) - 성공`() {
        // given
        val reqBody = mapOf(
            "pageNum" to "1",
            "pageVolume" to "50",
        )

        // when
        val resultAction = getRequest(MY_CONTENTS_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 문제 - 성공`() {
        // given
        val profileId = SUCCESS_ID

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 문제(페이징) - 성공`() {
        // given
        val profileId = SUCCESS_ID
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 문제 - 실패`() {
        // given
        val profileId = FAIL_ID

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
        assertExMsg(resultAction, MathContentsReadController.NOT_EXIST_MEMBER)
    }

    @Test
    fun `문제 목록 조회 - 성공`() {
        // given
        for (srchType in MathContentsSearchRequest.SearchType.entries) {
            val reqBody = mapOf(
                "searchType" to srchType.name,
                "unitId" to "21001",
            )

            // when
            val resultAction = getRequest(LIST_CONTENTS_URL, reqBody)

            // then
            assert2xx(resultAction)
        }
    }

    @Test
    fun `문제 목록 조회(페이징) - 성공`() {
        // given
        val reqBody = mapOf(
            "searchType" to MathContentsSearchRequest.SearchType.Subject.name,
            "unitId" to "21001",
            "pageNum" to "0",
            "pageVolume" to "100",
        )

        // when
        val resultAction = getRequest(LIST_CONTENTS_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `내 저장소 문제 조회 - 성공`() {
        // when
        val resultAction = getRequest(REPO_CONTENTS_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `내 저장소 문제 조회(페이징) - 성공`() {
        // given
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )

        // when
        val resultAction = getRequest(REPO_CONTENTS_URL, reqBody)

        // then
        assert2xx(resultAction)
    }
}