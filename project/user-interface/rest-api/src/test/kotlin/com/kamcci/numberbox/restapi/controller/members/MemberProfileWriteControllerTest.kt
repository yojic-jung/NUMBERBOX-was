package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.file.FileFixture.getMultipartFile
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.HandlerMethodValidationException
import java.util.*

@WebMvcUnitTest
class MemberProfileWriteControllerTest @Autowired constructor(
    private val fileUseCase: FileUseCase,
    private val memberProfileReadCase: MemberProfileReadCase,
    private val memberProfileFollowReadCase: MemberProfileFollowReadCase,
    private val memberFollowReadCase: MemberFollowReadCase,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/member/profile"
        const val PROFILE_REG_URL = PREFIX
        const val PROFILE_IMG_URL = "$PREFIX/img"
        const val NICKNAME_CHNG_URL = "$PREFIX/nickname"
        const val MY_PROFILE_URL = PREFIX
    }

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
        val file = listOf(getMultipartFile("imgFile", "imgFile.png"))
        `when`(fileUseCase.upload(any(), any())).thenReturn(FileNameVo("", ""))

        // when
        val resultActions = putMultipartForm(PROFILE_IMG_URL, file)

        // then
        assert2xx(resultActions)
    }


    @Test
    fun `프로필 이미지 등록 - 실패`() {
        // given
        val file = listOf(getMultipartFile("imgFile", "imgFile.ppt"))

        // when
        val resultAction = putMultipartForm(PROFILE_IMG_URL, file)

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
        `when`(memberProfileFollowReadCase.readFollowingProfileByMemberId(any())).thenReturn(list)
        `when`(memberProfileFollowReadCase.readFollowerProfileByMemberId(any())).thenReturn(list)

        // when
        val resultAction = getRequest(MY_PROFILE_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 성공`() {
        `when`(memberProfileReadCase.readProfileIdByMemberId(any())).thenReturn(1L)
        `when`(memberFollowReadCase.existFollow(any(), any())).thenReturn(true)
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
        assertException(resultAction, BusinessInValidException::class)
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