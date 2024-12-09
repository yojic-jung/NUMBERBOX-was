package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.HandlerMethodValidationException
import java.util.*

@WebMvcUnitTest
class MemberProfileWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PROFILE_REG_URL = "/member/profile"
        const val PROFILE_IMG_URL = "/member/profile/img"
        const val NICKNAME_CHNG_URL = "/member/profile/nickname"
        const val MY_PROFILE_URL = "/member/profile"
    }

    @Autowired
    lateinit var memberProfileReadCase: MemberProfileReadCase

    @Autowired
    lateinit var memberFollowReadCase: MemberFollowReadCase

    @Test
    fun `프로필 등록 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)

        //when
        val resultAction = putRequest(PROFILE_REG_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `프로필 등록 - 실패(유효하지 않은 프로필 타입)`() {
        // given
        val reqBody = mapOf("profileType" to "123")

        //when
        val resultAction = putRequest(PROFILE_REG_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
    }


    @Test
    fun `프로필 이미지 등록 - 성공`() {
        // given
        val file = MockMultipartFile("imgFile", "originalFilename", "image/jpeg", "12345".toByteArray())

        // when
        val resultActions =
            mockMvc.perform(
                multipart(PROFILE_IMG_URL)
                    .file(file)
                    .with { request ->
                        request.method = "PUT"
                        request
                    })

        // then
        assert2xx(resultActions)
    }


    @Test
    fun `프로필 이미지 등록 - 실패`() {
        // given
        val file = MockMultipartFile("noName", "originalFilename", "image/jpeg", "12345".toByteArray())

        // when
        val resultAction =
            mockMvc.perform(
                multipart(PROFILE_IMG_URL)
                    .file(file)
                    .with { request ->
                        request.method = "PUT"
                        request
                    }
            )

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `프로필 닉네임 변경 - 성공`() {
        // given
        val reqBody = mapOf("nickname" to "nickname")

        //when
        val resultAction = putRequest(NICKNAME_CHNG_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `프로필 닉네임 변경 - 실패`() {
        // given
        val reqBody = mapOf("nickname" to "")

        //when
        val resultAction = putRequest(NICKNAME_CHNG_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }


    @Test
    fun `내 프로필 보기 - 성공`() {
        val memberProfileVo = MemberProfileVo(1L, UUID.randomUUID(), "", "", "", ProfileType.Teacher)
        val list = listOf(memberProfileVo)
        `when`(memberProfileReadCase.readByMemberId(any())).thenReturn(memberProfileVo)
        `when`(memberProfileReadCase.readFollowingProfileByMemberId(any())).thenReturn(list)
        `when`(memberProfileReadCase.readFollowerProfileByMemberId(any())).thenReturn(list)

        // when
        val resultAction = getRequest(MY_PROFILE_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 성공`() {
        `when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(1L)
        `when`(memberFollowReadCase.isFollowing(any(), any())).thenReturn(true)
        `when`(memberFollowReadCase.countFollower(any())).thenReturn(1)

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/1")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 미존재)`() {
        // given
        `when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(null)

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/1")

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessValidException::class)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 id 양수 아님)`() {
        // when
        val resultAction = getRequest("$MY_PROFILE_URL/0")

        // then
        assert4xx(resultAction)
        assertException(resultAction, HandlerMethodValidationException::class)
    }
}