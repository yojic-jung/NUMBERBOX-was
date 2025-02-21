package com.kamcci.numberbox.restapi.util.math

import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathCategoryUnitVo
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathUnitUtilTest {
    @Test
    fun `전체 단원 정보 반환 - 성공`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitGroupMap = MathUnitUtil.extractUnitMap(unitCategoryDummy)

        assertThat(unitGroupMap.get("subjectList")!!.size).isOne()
        assertThat(unitGroupMap.get("firUnitList")!!.size).isOne()
        assertThat(unitGroupMap.get("secUnitList")!!.size).isEqualTo(2)
        assertThat(unitGroupMap.get("thrUnitList")!!.size).isEqualTo(4)
    }

    @Test
    fun `학년 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.Subject, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(4)
    }

    @Test
    fun `학년 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.Subject, 41001)

        // then
        assertThat(unitIdList.size).isZero()
    }

    @Test
    fun `대단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.FirUnit, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(4)
    }

    @Test
    fun `대단원 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.FirUnit, 41001)

        // then
        assertThat(unitIdList.size).isZero()
    }

    @Test
    fun `중단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.SecUnit, 21001)

        // then
        assertThat(unitIdList.size).isEqualTo(2)
    }

    @Test
    fun `중단원 같은 단원 id 추출 - 실패`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.SecUnit, 41001)

        // then
        assertThat(unitIdList.size).isZero()
    }

    @Test
    fun `소단원 같은 단원 id 추출 - 성공`() {
        // given
        val unitCategoryDummy = getMathCategoryUnitVo()

        // when
        val unitIdList =
            MathUnitUtil.getUnitIdList(unitCategoryDummy, MathContentsSearchRequest.SearchType.ThrUnit, 21001)

        // then
        assertThat(unitIdList.size).isOne()
    }
}