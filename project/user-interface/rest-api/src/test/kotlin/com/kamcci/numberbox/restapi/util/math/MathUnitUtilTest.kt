package com.kamcci.numberbox.restapi.util.math

import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.dummy.math.MathCategoryFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathUnitUtilTest {
    @Test
    fun `전체 단원 정보 반환 - 성공`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitGroupMap = MathUnitUtil.extractUnitMap(unitCategoryDummy)

        assertThat(unitGroupMap.get("subjectList")!!.size).isEqualTo(1)
        assertThat(unitGroupMap.get("firUnitList")!!.size).isEqualTo(1)
        assertThat(unitGroupMap.get("secUnitList")!!.size).isEqualTo(2)
        assertThat(unitGroupMap.get("thrUnitList")!!.size).isEqualTo(4)
    }

    @Test
    fun `학년 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.Subject, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(4)
    }

    @Test
    fun `학년 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.Subject, 41001)

        // then
        assertThat(unitIdList.size).isEqualTo(0)
    }

    @Test
    fun `대단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.FirUnit, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(4)
    }

    @Test
    fun `대단원 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.FirUnit, 41001)

        // then
        assertThat(unitIdList.size).isEqualTo(0)
    }

    @Test
    fun `중단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.SecUnit, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(2)
    }

    @Test
    fun `중단원 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.SecUnit, 41001)

        // then
        assertThat(unitIdList.size).isEqualTo(0)
    }

    @Test
    fun `소단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = MathCategoryFixture.getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.ThrUnit, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(1)
    }
}