package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.math.MathUnitFixture.getMathCategoryUnitVo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MathMenuReadControllerTest(
    @Autowired
    private val mathCategoryUnitReadCase: MathCategoryUnitReadCase,
) : BaseMockMvcTest() {

    companion object {
        const val PREFIX = "/public/math/menu"
        const val UNIT_URL = "$PREFIX/unit"
        const val TYPE_URL = "$PREFIX/type"
        const val SHORTCUTKEY_URL = "$PREFIX/shortCutKey"
        const val IPSI_YEAR_URL = "$PREFIX/ipsi-year"
    }

    @Test
    fun `단원 카테고리 조회 - 성공`() {
        // given
        val unitList = getMathCategoryUnitVo()
        `when`(mathCategoryUnitReadCase.readAll()).thenReturn(unitList)

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
    fun `입시문제 연도 조회 - 성공`() {
        // when
        val resultAction = getRequest(IPSI_YEAR_URL)

        // then
        assert2xx(resultAction)
    }
}