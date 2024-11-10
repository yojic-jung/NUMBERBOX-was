package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
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
class MemberProfileControllerTest : BaseMockMvcTest() {
    companion object {
        const val PROFILE_REG_URL = "/member/profile"
        const val PROFILE_IMG_URL = "/member/profile/img"
        const val NICKNAME_CHNG_URL = "/member/profile/nickname"
        const val MY_PROFILE_URL = "/member/profile"
    }

    @Autowired
    lateinit var memberProfileModifyUseCase: MemberProfileModifyUseCase

    @Autowired
    lateinit var memberProfileReadUseCase: MemberProfileReadUseCase

    @Autowired
    lateinit var memberFollowReadUseCase: MemberFollowReadUseCase

    @Test
    fun `프로필 등록 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        `when`(memberProfileModifyUseCase.updateProfileTypeByMemberId(any(), any())).thenReturn(true)

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
        `when`(memberProfileReadUseCase.readByMemberId(any())).thenReturn(memberProfileVo)
        `when`(memberProfileReadUseCase.readFollowingProfileByMemberId(any())).thenReturn(list)
        `when`(memberProfileReadUseCase.readFollowerProfileByMemberId(any())).thenReturn(list)

        // when
        val resultAction = getRequest(MY_PROFILE_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 성공`() {
        `when`(memberProfileReadUseCase.readProfileIdByMemberId(any())).thenReturn(1L)
        `when`(memberFollowReadUseCase.isFollowing(any(), any())).thenReturn(true)
        `when`(memberFollowReadUseCase.countFollower(any())).thenReturn(1)

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/1")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 미존재)`() {
        // given
        `when`(memberProfileReadUseCase.readProfileIdByMemberId(any())).thenReturn(null)

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