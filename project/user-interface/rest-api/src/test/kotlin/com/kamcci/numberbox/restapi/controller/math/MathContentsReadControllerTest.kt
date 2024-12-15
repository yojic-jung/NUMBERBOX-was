package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.dummy.math.MathCategoryFixture.getMathCategoryUnitVo
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathContentsOnlyVo
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathContentsVo
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathInHouseContentsVo
import com.kamcci.numberbox.restapi.dummy.math.MathContentsFixture.getMathIpsiContentsVo
import com.kamcci.numberbox.restapi.dummy.member.MemberProfileFixture.getMemberProfileVo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MathContentsReadControllerTest @Autowired constructor(
    private val memberProfileReadCase: MemberProfileReadCase,
    private val mathContentsReadCase: MathContentsReadCase,
    private val mathCategoryUnitReadCase: MathCategoryUnitReadCase,
    private val mathContentsRepoReadCase: MathContentsRepoReadCase,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/content"
        const val CONTENTS_URL = PREFIX
        const val MY_CONTENTS_URL = "$PREFIX/my"
        const val USER_CONTENTS_URL = "$PREFIX/user"
        const val LIST_CONTENTS_URL = "$PREFIX/list"
        const val REPO_CONTENTS_URL = "$PREFIX/repo"
    }

    @BeforeEach
    fun setUp() {
        Mockito.reset(mathContentsReadCase) // Mock 상태 리셋
    }

    @Test
    fun `문제 id로 조회 - 성공`() {
        // given
        val contentsId = 1
        val reqBody = mapOf(
            "contentsOnly" to "true",
            "contentsClassify" to "InHouse"
        )
        Mockito.`when`(mathContentsReadCase.readContentsOnly(any(), any()))
            .thenReturn(getMathContentsOnlyVo())

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회 - 실패`() {
        // given
        val contentsId = 2
        val reqBody = mapOf(
            "contentsOnly" to "true",
            "contentsClassify" to "InHouse"
        )

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)
        Mockito.`when`(mathContentsReadCase.readContentsOnly(any(), any()))
            .thenReturn(null)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessValidException::class)
        assertExMsg(resultAction, MathContentsReadController.NOT_EXIST_CONTENTS)
    }

    @Test
    fun `문제 id로 조회(자체제작 문제) - 성공`() {
        // given
        val contentsId = 1
        val reqBody = mapOf(
            "contentsOnly" to "false",
            "contentsClassify" to "InHouse"
        )
        Mockito.`when`(mathContentsReadCase.readInHouseContentsById(any()))
            .thenReturn(getMathInHouseContentsVo())

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회(입시 문제) - 성공`() {
        // given
        val contentsId = 1
        val reqBody = mapOf(
            "contentsClassify" to "Ipsi"
        )
        Mockito.`when`(mathContentsReadCase.readIpsiContentsById(any()))
            .thenReturn(getMathIpsiContentsVo())
        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 id로 조회(사용자 제작 문제) - 성공`() {
        // given
        val contentsId = 1
        val reqBody = mapOf(
            "contentsOnly" to "false",
            "contentsClassify" to "UserCustom"
        )
        Mockito.`when`(mathContentsReadCase.readById(any()))
            .thenReturn(getMathContentsVo())

        // when
        val resultAction = getRequest("$CONTENTS_URL/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 문제 - 성공`() {
        // given
        Mockito.`when`(mathContentsReadCase.readById(any())).thenReturn(getMathContentsVo())

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
        val profileId = 1
        Mockito.`when`(memberProfileReadCase.readByProfileId(any()))
            .thenReturn(getMemberProfileVo())

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 문제(페이징) - 성공`() {
        // given
        val profileId = 1
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )
        Mockito.`when`(memberProfileReadCase.readByProfileId(any()))
            .thenReturn(getMemberProfileVo())

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `사용자 문제 - 실패`() {
        // given
        val profileId = 1
        Mockito.`when`(memberProfileReadCase.readByProfileId(any()))
            .thenReturn(null)

        // when
        val resultAction = getRequest("$USER_CONTENTS_URL/$profileId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessValidException::class)
        assertExMsg(resultAction, MathContentsReadController.NOT_EXIST_MEMBER)
    }

    @Test
    fun `문제 목록 조회 - 성공`() {
        // given
        for (srchType in MathContentsSearchRequest.SearchType.entries) {
            val reqBody = mapOf(
                "searchType" to srchType.name,
                "unitId" to "21001",
                "pageNum" to "0",
                "pageVolume" to "100",
            )
            Mockito.`when`(mathCategoryUnitReadCase.readAll()).thenReturn(getMathCategoryUnitVo())

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
        Mockito.`when`(mathCategoryUnitReadCase.readAll()).thenReturn(getMathCategoryUnitVo())

        // when
        val resultAction = getRequest(LIST_CONTENTS_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `내 저장소 문제 조회 - 성공`() {
        // given
        Mockito.`when`(mathContentsRepoReadCase.readContentsIdByMemberId(any())).thenReturn(listOf(1, 2, 3))

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
        Mockito.`when`(mathContentsRepoReadCase.readContentsIdByMemberId(any())).thenReturn(listOf(1, 2, 3))

        // when
        val resultAction = getRequest(REPO_CONTENTS_URL, reqBody)

        // then
        assert2xx(resultAction)
    }
}