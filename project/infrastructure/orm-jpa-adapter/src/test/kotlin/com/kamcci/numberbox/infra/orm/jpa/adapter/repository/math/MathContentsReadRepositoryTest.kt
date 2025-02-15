package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsReadRepositoryTest(
    @Autowired
    private val mathContentsReadRepository: MathContentsReadRepository
) {
    @Test
    fun `문제 id로 조회`() {
        // given
        val contentsId = 1L

        // when
        val mathContents = mathContentsReadRepository.readById(contentsId)

        // then
        assertThat(mathContents).isNotNull
    }

    @Test
    fun `문제 id로 페이징 조회`() {
        // given
        val contentsIdList = listOf(1L)
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readById(contentsIdList, pageReq)

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `문제 id와 memberId로 조회`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val contentsId = 1L

        // when
        val contents = mathContentsReadRepository.readDetailByContentsIdAndMemberId(contentsId, memberId)

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `수학 문제 조회(서비스 가능 여부 Null) - 좋아요 정보 제외`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByMemberId(memberId, null, pageReq)

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `수학 문제 조회(서비스 가능만) - 좋아요 정보 제외`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList =
            mathContentsReadRepository.readDetailByMemberId(memberId, ContentsSvcPosbSttsType.Release, pageReq)

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `수학 문제 조회(서비스 가능 여부 Null) - 좋아요 정보 포함`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByMemberId(
            memberId,
            memberId,
            null,
            pageReq
        )

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `수학 문제 조회(서비스 가능만) - 좋아요 정보 포함`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByMemberId(
            memberId,
            memberId,
            ContentsSvcPosbSttsType.Release,
            pageReq
        )

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `단원으로 문제 조회`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val unitIdList = listOf(22003)
        val pageReq = PageRequestImpl(0, 10L)

        // when
        val contentsList = mathContentsReadRepository.readDetailByUnitId(memberId, unitIdList, pageReq)

        // then
        assertThat(contentsList.size).isGreaterThan(0)
    }

    @Test
    fun `자체제작 수학 문제와 유사문제 출처 정보`() {
        // given
        val contentsId = 1L

        // when
        val contents = mathContentsReadRepository.readInHouseContentsById(contentsId)

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `입시 수학 문제`() {
        // given
        val contentsId = 4907L

        // when
        val contents = mathContentsReadRepository.readIpsiContentsById(contentsId)

        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `변형문제 갯수 조회`() {
        // given
        val contentsId = 1L

        // when
        val transContentCnt = mathContentsReadRepository.readTransContCntById(contentsId)

        // then
        assertThat(transContentCnt).isZero()
    }

    @Test
    fun `문제만 조회`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val contentsId = 1L

        // when
        val contents = mathContentsReadRepository.readContentsOnly(contentsId, memberId)


        // then
        assertThat(contents).isNotNull
    }

    @Test
    fun `단원으로 수학문제 카운트`() {
        // given
        val unitId = listOf(22003)

        // when
        val count = mathContentsReadRepository.countByUnitId(unitId)

        // then
        assertThat(count).isGreaterThan(0)
    }

    @Test
    fun `수학문제 id 존재 여부 - 존재`() {
        // given
        val contentsId = 1L

        // when
        val isExist = mathContentsReadRepository.existById(contentsId)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `수학문제 id 존재 여부 - 미존재`() {
        // given
        val contentsId = 9999999L

        // when
        val isExist = mathContentsReadRepository.existById(contentsId)

        // then
        assertThat(isExist).isFalse()
    }
}