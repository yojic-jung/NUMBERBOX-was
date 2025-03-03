package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getInHouseContentsDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.util.*

@TcDBJpaTest
class MathContentsEntityTest(
    @Autowired
    private val em: EntityManager
) {
    private val dummyEntity = getInHouseContentsDummyEntity()

    @Test
    fun `MathContentsEntity 조회 - 성공`() {
        // given
        val id = dummyEntity.contentsId

        // when
        val mathContentsEntity = em.find(MathContentsEntity::class.java, id)

        // then
        assertEntityProperty(mathContentsEntity, id)
    }

    private fun assertEntityProperty(mathContentsEntity: MathContentsEntity, id: Long) {
        assertThat(mathContentsEntity.id).isEqualTo(id)
        assertThat(mathContentsEntity.unitId).isEqualTo(dummyEntity.unitId)
        assertThat(mathContentsEntity.typeId).isEqualTo(dummyEntity.typeId)
        assertThat(mathContentsEntity.memberId).isEqualTo(dummyEntity.memberId)
        assertThat(mathContentsEntity.contents).isNotNull()
        assertThat(mathContentsEntity.solution).isNotNull()
        assertThat(mathContentsEntity.contentsImg).isNull()
        assertThat(mathContentsEntity.solutionImg).isNull()
        assertThat(mathContentsEntity.imgPath).isNull()
        assertThat(mathContentsEntity.solutionImgPath).isNull()
        assertThat(mathContentsEntity.firNo).isEmpty()
        assertThat(mathContentsEntity.secNo).isEmpty()
        assertThat(mathContentsEntity.thrNo).isEmpty()
        assertThat(mathContentsEntity.fourNo).isEmpty()
        assertThat(mathContentsEntity.fifNo).isEmpty()
        assertThat(mathContentsEntity.multiChoiceType).isEqualTo(MultiChoiceType.Essay)
        assertThat(mathContentsEntity.answer).isNotNull
        assertThat(mathContentsEntity.choiceAnswer).isNull()
        assertThat(mathContentsEntity.orgSrcRef).isNull()
        assertThat(mathContentsEntity.orgSrcNo).isZero()
        assertThat(mathContentsEntity.quesLevel).isEqualTo(dummyEntity.quesLevel)
        assertThat(mathContentsEntity.transConCnt).isEqualTo(dummyEntity.transConCtn)
        assertThat(mathContentsEntity.contentsClassify).isEqualTo(ContentsClassifyType.InHouse)
        assertThat(mathContentsEntity.svcPosbStts).isEqualTo(ContentsSvcPosbSttsType.Release)
        assertThat(mathContentsEntity.orgContentsId).isZero()
        assertThat(mathContentsEntity.ansExistStts).isTrue()
        assertThat(mathContentsEntity.sysCreateDate).isNotNull()
        assertThat(mathContentsEntity.sysUpdateDate).isNotNull()
    }

    @Test
    fun `MathContentsEntity with licenceEntity 영속화 - 성공`() {
        // given
        val mathContentsEntity = getMathContentsSaveEntity()
        val licenseEntity = getContentsLicenseSaveEntity()
        mathContentsEntity.mathContentsLicenses = mutableListOf(licenseEntity)
        licenseEntity.mathContents = mathContentsEntity

        // when
        em.persist(mathContentsEntity)
        em.flush()
        em.clear()

        // then
        assertThat(mathContentsEntity.id).isPositive()
    }

    @Test
    fun `MathContentsEntity 연관관계 - 성공`() {
        // given
        val id = dummyEntity.contentsId

        // when
        val mathContentsEntity = em.find(MathContentsEntity::class.java, id)

        // then
        assertThat(mathContentsEntity.mathContentsSimilarSrc[0].id).isOne()
        assertThat(mathContentsEntity.mathContentsLicenses[0].id).isOne()
        assertThat(mathContentsEntity.mathContentsIpsiSrc.size).isZero()
    }


    private fun getMathContentsSaveEntity(): MathContentsEntity {
        val now = LocalDateTime.now()

        return MathContentsEntity().apply {
            unitId = dummyEntity.unitId
            typeId = dummyEntity.typeId
            memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
            contents = ""
            contentsImg = ""
            imgPath = ""
            solution = ""
            solutionImg = ""
            solutionImgPath = ""
            firNo = ""
            secNo = ""
            thrNo = ""
            fourNo = ""
            fifNo = ""
            multiChoiceType = MultiChoiceType.Essay
            answer = ""
            choiceAnswer = "1"
            orgSrcRef = ""
            orgSrcNo = 1
            quesLevel = 1
            ansExistStts = true
            svcPosbStts = ContentsSvcPosbSttsType.Release
            contentsClassify = ContentsClassifyType.InHouse
            orgContentsId = 0
            transConCnt = 0
            sysCreateDate = now
            sysUpdateDate = now
        }
    }

    private fun getContentsLicenseSaveEntity(): MathContentsLicenseEntity {
        val now = LocalDateTime.now()
        return MathContentsLicenseEntity().apply {
            onlineLicStts = true
            perLicStts = true
            perLicPrice = 10000
            entLicStts = true
            entLicPrice = 10000
            shareStts = true
            sysCreateDate = now
            sysUpdateDate = now
        }
    }

}