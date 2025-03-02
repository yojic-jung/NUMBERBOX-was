package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@WebMvcUnitTest
class MathContentsLikeReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/like-repo"
        const val LIKE_REPO_URL = "$PREFIX/content"
    }

    @Test
    fun `좋아요 및 저장소 여부 - 성공`() {
        // given
        val contentsId = 1L

        // when
        val resultAction = getRequest("$LIKE_REPO_URL/$contentsId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `좋아요 및 저장소 여부 - 실패`() {
        // given - id 정수 타입 아님
        val contentsId = "ㅁㅇ"

        // when
        val resultAction = getRequest("$LIKE_REPO_URL/$contentsId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentTypeMismatchException::class)
    }
}