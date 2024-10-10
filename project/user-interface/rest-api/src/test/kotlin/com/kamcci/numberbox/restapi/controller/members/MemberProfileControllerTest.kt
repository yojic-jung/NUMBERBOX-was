package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
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
        assertThat(removeQuotes(takeJsonResponseData(resultAction).get("isRegisted"))).isEqualTo("true")
    }

    @Test
    fun `프로필 등록 - 실패(유효하지 않은 프로필 타입)`() {
        // given
        val reqBody = mapOf("profileType" to "123")

        //when
        val resultAction = putRequest(PROFILE_REG_URL, reqBody)

        // then
        assert4xx(resultAction)
    }


    @Test
    fun `프로필 이미지 등록 - 성공`() {
        // given
        val file = MockMultipartFile("imgFile", "originalFilename", "image/jpeg", "12345".toByteArray())
//        `when`(memberProfileModifyUseCase.updateImgByMemberId(any(), any(), any())).thenReturn(true)

        // when
        val resultActions =
            mockMvc.perform(multipart(PROFILE_IMG_URL).file(file))

        // then
        assert2xx(resultActions)
    }


    @Test
    fun `프로필 이미지 등록 - 실패`() {
        // given
        val file = MockMultipartFile("noName", "originalFilename", "image/jpeg", "12345".toByteArray())
//        `when`(memberProfileModifyUseCase.updateImgByMemberId(any(), any(), any())).thenReturn(true)

        // when
        val resultActions =
            mockMvc.perform(multipart(PROFILE_IMG_URL).file(file))

        // then
        assert4xx(resultActions)
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
    }


    @Test
    fun `내 프로필 보기 - 성공`() {
        val memberProfileVo = MemberProfileVo(1L, UUID.randomUUID(), "", "", "", ProfileType.Teacher)
        val list = listOf(memberProfileVo)
        `when`(memberProfileReadUseCase.findByMemberId(any())).thenReturn(memberProfileVo)
        `when`(memberProfileReadUseCase.findFollowingProfileByMemberId(any())).thenReturn(list)
        `when`(memberProfileReadUseCase.findFollowerProfileByMemberId(any())).thenReturn(list)

        // when
        val resultAction = getRequest(MY_PROFILE_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 성공`() {
        `when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(1L)
        `when`(memberFollowReadUseCase.isMyFollower(any(), any())).thenReturn(true)
        `when`(memberFollowReadUseCase.countFollower(any())).thenReturn(1)

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/1")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 미존재)`() {
        // given
        `when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(null)

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/1")

        // then
        assert4xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 id 양수 아님)`() {
        // when
        val resultAction = getRequest("$MY_PROFILE_URL/0")

        // then
        assert4xx(resultAction)
    }
}