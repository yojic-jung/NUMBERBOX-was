package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.file.FileSampleData.getMultipartFile
import org.junit.jupiter.api.Test
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class MemberProfileWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/member/profile"
        const val PROFILE_REG_URL = PREFIX
        const val PROFILE_IMG_URL = "$PREFIX/img"
        const val NICKNAME_CHNG_URL = "$PREFIX/nickname"
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

}