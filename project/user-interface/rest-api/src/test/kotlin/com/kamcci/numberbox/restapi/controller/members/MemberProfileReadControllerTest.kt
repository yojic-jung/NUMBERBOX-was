package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.web.method.annotation.HandlerMethodValidationException

@WebMvcUnitTest
class MemberProfileReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/member/profile"
        const val MY_PROFILE_URL = PREFIX
    }

    @Test
    fun `내 프로필 보기 - 성공`() {
        // when
        val resultAction = getRequest(MY_PROFILE_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 성공`() {
        // given
        val profileId = 1L

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/$profileId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 미존재)`() {
        // given - 미존재 프로필 id
        val profileId = 2

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/$profileId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }

    @Test
    fun `다른 사람 프로필 보기 - 실패(프로필 id 양수 아님)`() {
        // given - 양수 아닌 Id
        val profileId = 0

        // when
        val resultAction = getRequest("$MY_PROFILE_URL/$profileId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, HandlerMethodValidationException::class)
    }
}