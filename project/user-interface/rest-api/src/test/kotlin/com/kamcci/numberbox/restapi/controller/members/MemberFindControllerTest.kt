package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class MemberFindControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/public/member"
        const val FIND_EMAIL_URL = "$PREFIX/findEmail"
        const val FIND_PASSWD_URL = "$PREFIX/findPassword"
    }

    @Test
    fun `이메일 찾기 - 성공`() {
        // given
        val reqBody = mapOf(
            "userName" to "이름",
            "phoneNumber" to "01012341234"
        )

        //when
        val resultAction = getRequest(FIND_EMAIL_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `이메일 찾기 - 실패(유효하지 않은 이름)`() {
        // given
        val reqBody = mapOf(
            "userName" to "이",
            "phoneNumber" to "01012341234"
        )

        //when
        val resultAction = getRequest(FIND_EMAIL_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `이메일 찾기 - 실패(유효하지 않은 휴대폰 번호)`() {
        // given
        val reqBody = mapOf(
            "userName" to "이름",
            "phoneNumber" to "010123456789"
        )

        //when
        val resultAction = getRequest(FIND_EMAIL_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `임시 비밀번호 발급 - 실패(유효하지 않은 이메일)`() {
        // given
        val reqBody = mapOf("email" to "email")

        //when
        val resultAction = putRequest(FIND_PASSWD_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `임시 비밀번호 발급 - 성공`() {
        // given
        val reqBody = mapOf("email" to "test@test.com")

        //when
        val resultAction = putRequest(FIND_PASSWD_URL, reqBody)

        // then
        assert2xx(resultAction)
    }
}