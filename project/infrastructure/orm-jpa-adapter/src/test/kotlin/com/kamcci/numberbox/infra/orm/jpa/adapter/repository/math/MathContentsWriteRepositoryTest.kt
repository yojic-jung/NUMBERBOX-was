package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConLicenseModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathContentsModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getInHouseContentsDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getIpsiContentsDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val mathContentsWriteRepository: MathContentsWriteRepository
) {
    private val inHouseDummyEntity = getInHouseContentsDummyEntity()

    @Test
    fun `사용자 수학문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveWithLicense(
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
                getMathConLicenseModifyDto()
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `자체제작 수학문제 제작`() {
        // given
        val modifyDto = getMathContentsModifyDto("any", listOf("1", "2")) // 주관식, 객관식 정답 존재

        // when
        val id =
            mathContentsWriteRepository.saveWithSimilarSrc(
                ContentsSvcPosbSttsType.Release,
                modifyDto,
                getMathConSimilarSrcCreateDto()
            )
        em.flush()
        em.clear()

        assertThat(id).isPositive()
    }

    @Test
    fun `입시 수학문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveWithIpsiSrc(
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
                getMathConIpsiSrcModifyDto()
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `변형문제 제작`() {
        // when
        val id =
            mathContentsWriteRepository.saveTransContents(
                inHouseDummyEntity.contentsId,
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("123", listOf("1", "2"))
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `변형문제 수 수정`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId

        // when
        val executeRowCnt = mathContentsWriteRepository.incrementTransConCntById(contentsId)

        // then
        assertThat(executeRowCnt).isOne()
    }

    @Test
    fun `저작권 수정`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId

        // when
        val id =
            mathContentsWriteRepository.updateWithLicense(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
                getMathConLicenseModifyDto()
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `자체 제작문제 수정`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId

        // when
        val id =
            mathContentsWriteRepository.updateWithSimilarSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
                getMathConSimilarSrcCreateDto()
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `입시문제 수정`() {
        // given
        val contentsId = getIpsiContentsDummyEntity().contentsId

        // when
        val id =
            mathContentsWriteRepository.updateWithIpsiSrc(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
                getMathConIpsiSrcModifyDto()
            )
        em.flush()
        em.clear()

        // then
        assertThat(id).isEqualTo(contentsId)
    }

    @Test
    fun `변형문제 수정`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId

        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateTransContents(
                contentsId,
                ContentsSvcPosbSttsType.Release,
                getMathContentsModifyDto("any", listOf("1", "2")),
            )
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }


    @Test
    fun `서비스 가능 상태 변경 - contentsId로 변경`() {
        // given
        val contentsId = inHouseDummyEntity.contentsId
        val memberId = inHouseDummyEntity.memberId

        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateContentsClassifyType(contentsId, memberId, ContentsClassifyType.Deleted)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }

    @Test
    fun `서비스 가능 상태 변경 - 사용자의 제작문제 전체 변경`() {
        // given
        val memberId = inHouseDummyEntity.memberId

        // when
        val executeRowCnt =
            mathContentsWriteRepository.updateContentsClassifyType(memberId, ContentsClassifyType.Deleted)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }
}