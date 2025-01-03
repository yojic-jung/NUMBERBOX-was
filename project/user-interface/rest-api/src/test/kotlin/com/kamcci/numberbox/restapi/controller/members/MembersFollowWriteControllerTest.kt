package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MembersFollowWriteControllerTest(
    @Autowired
    private val memberProfileReadCase: MemberProfileReadCase
) : BaseMockMvcTest() {


    companion object {
        const val FOLLOWING_URL = "/member/following"
    }

    @Test
    fun `팔로잉 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(1L)

        //when
        val resultAction = postRequest("$FOLLOWING_URL/2", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `팔로잉 - 실패(미존재 계정)`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(null)

        //when
        val resultAction = postRequest("$FOLLOWING_URL/2", reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }

    @Test
    fun `팔로잉 취소 - 성공`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(1)

        //when
        val resultAction = delRequest("$FOLLOWING_URL/2", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `팔로잉 취소 - 실패(프로필 미존재)`() {
        // given
        val reqBody = mapOf("profileType" to ProfileType.Teacher.name)
        Mockito.`when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(null)

        //when
        val resultAction = delRequest("$FOLLOWING_URL/2", reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }
}