package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsFixture
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsLicenseFixture
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MembersFixture
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

@TcDBJpaTest
class MathContentsEntityTest(
    @Autowired
    private val em: EntityManager
) {

    @Test
    fun `MathContentsEntity 조회 - 성공`() {
        // given
        val id = 1L

        // when
        val mathContentsEntity = em.find(MathContentsEntity::class.java, id)

        // then
        assertThat(mathContentsEntity.id).isEqualTo(id)
        assertThat(mathContentsEntity.unitId).isEqualTo(22003)
        assertThat(mathContentsEntity.typeId).isEqualTo(1)
        assertThat(mathContentsEntity.memberId).isEqualTo(MembersFixture.getMemberId1())
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
        assertThat(mathContentsEntity.answer).isEqualTo("1")
        assertThat(mathContentsEntity.choiceAnswer).isNull()
        assertThat(mathContentsEntity.orgSrcRef).isNull()
        assertThat(mathContentsEntity.orgSrcNo).isEqualTo(0)
        assertThat(mathContentsEntity.quesLevel).isEqualTo(2)
        assertThat(mathContentsEntity.transConCnt).isEqualTo(0)
        assertThat(mathContentsEntity.contentsClassify).isEqualTo(ContentsClassifyType.InHouse)
        assertThat(mathContentsEntity.svcPosbStts).isEqualTo(ContentsSvcPosbSttsType.Release)
        assertThat(mathContentsEntity.orgContentsId).isEqualTo(0)
        assertThat(mathContentsEntity.ansExistStts).isTrue()
        assertThat(mathContentsEntity.sysCreateDate).isNotNull()
        assertThat(mathContentsEntity.sysUpdateDate).isNotNull()
    }

    @Test
    fun `MathContentsEntity with licenceEntity 영속화 - 성공`() {
        // given
        val mathContentsEntity = MathContentsFixture.getSaveEntity()
        val licenseEntity = MathContentsLicenseFixture.getSaveEntity()
        mathContentsEntity.mathContentsLicenses = mutableListOf(licenseEntity)
        licenseEntity.mathContents = mathContentsEntity

        // when
        em.persist(mathContentsEntity)
        em.flush()
        em.clear()

        // then
        assertThat(mathContentsEntity.id).isGreaterThan(0)
    }
    
    @Test
    fun `MathContentsEntity 연관관계 - 성공`() {
        // given
        val id = 1L

        // when
        val mathContentsEntity = em.find(MathContentsEntity::class.java, id)

        // then
        assertThat(mathContentsEntity.mathContentsSimilarSrc.get(0).id).isEqualTo(1)
        assertThat(mathContentsEntity.mathContentsLicenses.get(0).id).isEqualTo(1)
        assertThat(mathContentsEntity.mathContentsIpsiSrc.size).isEqualTo(0)
    }
}