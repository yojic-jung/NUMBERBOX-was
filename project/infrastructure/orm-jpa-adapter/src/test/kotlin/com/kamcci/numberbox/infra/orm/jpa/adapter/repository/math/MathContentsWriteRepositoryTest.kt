package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.enumeration.math.*
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.math.MathContentsSampleData
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsWriteRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val mathContentsWriteRepository: MathContentsWriteRepository
) {

    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
    private val licenseCreateDto = MathConLicenseModifyDto(true, true, true, true)
    private val similarSrcDto = MathConSimilarSrcCreateDto("", 1, 1, "", MathTypeClassifyType.Simple)
    private val ipsiCreateDto = MathConIpsiSrcModifyDto(IpsiManageInsType.Kice, 2022, 11, 55, IpsiPaperType.Ka, 1, 2)


    @Test
    fun `사용자 수학문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveWithLicense(
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
                licenseCreateDto
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `자체제작 수학문제 제작`() {
        // given
        val modifyDto = MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")) // 주관식, 객관식 정답 존재

        // when
        val id =
            mathContentsWriteRepository.saveWithSimilarSrc(ContentsSvcPosbSttsType.Release, modifyDto, similarSrcDto)
        em.flush()
        em.clear()

        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `입시 수학문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveWithIpsiSrc(
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
                ipsiCreateDto
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `변형문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveTransContents(
                1L,
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2"))
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `변형문제 수 수정`() {
        // given
        val contentsId = 1L

        // when
        val executeRowCnt = mathContentsWriteRepository.incrementTransConCntById(contentsId)

        // then
        assertThat(executeRowCnt).isOne()
    }

    @Test
    fun `저작권 수정`() {
        // given
        val contentsId = 1L

        // when
        val id =
            mathContentsWriteRepository.updateWithLicense(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
                licenseCreateDto
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `자체 제작문제 수정`() {
        // given
        val contentsId = 1L

        // when
        val id =
            mathContentsWriteRepository.updateWithSimilarSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
                similarSrcDto
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `입시문제 수정`() {
        // given
        val contentsId = 4907L

        // when
        val id =
            mathContentsWriteRepository.updateWithIpsiSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
                ipsiCreateDto
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `변형문제 수정`() {
        // given
        val contentsId = 1L

        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateTransContents(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                MathContentsSampleData.getMathContentsModifyDto("123", listOf("1", "2")),
            )
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }


    @Test
    fun `서비스 가능 상태 변경 - contentsId로 변경`() {
        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateContentsClassifyType(1L, memberId, ContentsClassifyType.Deleted)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }

    @Test
    fun `서비스 가능 상태 변경 - 사용자의 제작문제 전체 변경`() {
        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateContentsClassifyType(memberId, ContentsClassifyType.Deleted)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }
}