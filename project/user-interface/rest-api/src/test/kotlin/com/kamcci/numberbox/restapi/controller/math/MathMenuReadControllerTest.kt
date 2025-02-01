package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathMenuReadControllerTest : BaseMockMvcTest() {

    companion object {
        const val PREFIX = "/public/math/menu"
        const val UNIT_URL = "$PREFIX/unit"
        const val TYPE_URL = "$PREFIX/type"
        const val SHORTCUTKEY_URL = "$PREFIX/shortCutKey"
        const val IPSI_YEAR_URL = "$PREFIX/ipsi-year"
    }

    @Test
    fun `단원 카테고리 조회 - 성공`() {
        // when
        val resultAction = getRequest(UNIT_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `유형 카테고리 조회 - 성공`() {
        // given
        val unitIdParam = mapOf("unitId" to "21001,21002")

        // when
        val resultAction = getRequest(TYPE_URL, unitIdParam)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `수식 단축키 조회 - 성공`() {
        // when
        val resultAction = getRequest(SHORTCUTKEY_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `수식 단축키 조회(결과 미존재) - 성공`() {
        // when
        val resultAction = getRequest(SHORTCUTKEY_URL)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `입시문제 연도 조회 - 성공`() {
        // when
        val resultAction = getRequest(IPSI_YEAR_URL)

        // then
        assert2xx(resultAction)
    }
}