package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.NOT_EXIST_CONTENTS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getInHouseContentsDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getIpsiContentsDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsReadRepositoryTest(
    @Autowired
    private val mathContentsReadRepository: MathContentsReadRepository
) {
    private val inHouseDummyEntity = getInHouseContentsDummyEntity()

    @Test
    fun `문제 id로 조회`() {
        // given
        val contentsId = getInHouseContentsDummyEntity().contentsId

        // when
        val mathContents = mathContentsReadRepository.readById(contentsId)

        // then
        assertThat(mathContents).isNotNull
    }

    @Test
    fun `문제 id로 페이징 조회`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId
        val contentsIdList = listOf(contentsId)
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readById(contentsIdList, pageReq)

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `문제 id와 memberId로 조회`() {
        // when
        val contents =
            mathContentsReadRepository.readDetailByContentsIdAndMemberId(
                inHouseDummyEntity.contentsId,
                inHouseDummyEntity.memberId
            )

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `수학 문제 조회(서비스 가능 여부 Null) - 좋아요 정보 제외`() {
        // given
        val svcPosbSttsType = null
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList =
            mathContentsReadRepository.readDetailByMemberId(inHouseDummyEntity.memberId, svcPosbSttsType, pageReq)

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `수학 문제 조회(서비스 가능만) - 좋아요 정보 제외`() {
        // given
        val svcPosbSttsType = ContentsSvcPosbSttsType.Release
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList =
            mathContentsReadRepository.readDetailByMemberId(
                inHouseDummyEntity.memberId,
                svcPosbSttsType,
                pageReq
            )

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `수학 문제 조회(서비스 가능 여부 Null) - 좋아요 정보 포함`() {
        // given
        val svcPosbSttsType = null
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByMemberId(
            inHouseDummyEntity.memberId,
            inHouseDummyEntity.memberId,
            svcPosbSttsType,
            pageReq
        )

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `수학 문제 조회(서비스 가능만) - 좋아요 정보 포함`() {
        // given
        val svcPosbSttsType = ContentsSvcPosbSttsType.Release
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByMemberId(
            inHouseDummyEntity.memberId,
            inHouseDummyEntity.memberId,
            svcPosbSttsType,
            pageReq
        )

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `단원으로 문제 조회`() {
        // given
        val unitIdList = listOf(22003)
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList =
            mathContentsReadRepository.readDetailByUnitId(inHouseDummyEntity.memberId, unitIdList, pageReq)

        // then
        assertThat(contentsList.size).isPositive()
    }

    @Test
    fun `자체제작 수학 문제와 유사문제 출처 정보`() {
        // when
        val contents = mathContentsReadRepository.readInHouseContentsById(inHouseDummyEntity.contentsId)

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `입시 수학 문제`() {
        // given
        val ipsiDummyEntity = getIpsiContentsDummyEntity()

        // when
        val contents = mathContentsReadRepository.readIpsiContentsById(ipsiDummyEntity.contentsId)

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `변형문제 갯수 조회`() {
        // when
        val transContentCnt = mathContentsReadRepository.readTransContCntById(inHouseDummyEntity.contentsId)

        // then
        assertThat(transContentCnt).isEqualTo(inHouseDummyEntity.transConCtn)
    }

    @Test
    fun `문제만 조회`() {
        // given
        // when
        val contents =
            mathContentsReadRepository.readContentsOnly(inHouseDummyEntity.contentsId, inHouseDummyEntity.memberId)


        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `단원으로 수학문제 카운트`() {
        // given
        val unitIdList = listOf(inHouseDummyEntity.unitId)

        // when
        val count = mathContentsReadRepository.countByUnitId(unitIdList)

        // then
        assertThat(count).isPositive()
    }

    @Test
    fun `수학문제 id 존재 여부 - 존재`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId

        // when
        val isExist = mathContentsReadRepository.existById(contentsId)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `수학문제 id 존재 여부 - 미존재`() {
        // given
        val contentsId = NOT_EXIST_CONTENTS_ID

        // when
        val isExist = mathContentsReadRepository.existById(contentsId)

        // then
        assertThat(isExist).isFalse()
    }
}