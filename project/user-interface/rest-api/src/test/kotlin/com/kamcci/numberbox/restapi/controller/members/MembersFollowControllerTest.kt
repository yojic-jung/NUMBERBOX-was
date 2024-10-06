package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcUnitTest
class MembersFollowControllerTest : BaseMockMvcTest() {
    @Autowired
    lateinit var memberProfileReadUseCase: MemberProfileReadUseCase

    companion object {
        const val FOLLOWING_URL = "/member/follow"
    }

    @Test
    fun `팔로잉 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(1L)

        //when
        val resultAction = requestJsonPost("$FOLLOWING_URL/2", reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `팔로잉 - 실패(미존재 계정)`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(null)

        //when
        val resultAction = requestJsonPost("$FOLLOWING_URL/2", reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `팔로잉 취소 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(null)

        //when
        val resultAction = requestJsonDel("$FOLLOWING_URL/2", reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `팔로잉 취소 - 실패(프로필 미존재)`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadUseCase.findProfileIdByMemberId(any())).thenReturn(null)

        //when
        val resultAction = requestJsonDel("$FOLLOWING_URL/2", reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}