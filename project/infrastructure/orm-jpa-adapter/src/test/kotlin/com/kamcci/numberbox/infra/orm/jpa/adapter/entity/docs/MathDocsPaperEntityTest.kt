package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.getDocsPaperDummyEntity4Read
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsPaperEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathDocsPaperEntity 조회`() {
        // given
        val id = getDocsPaperDummyEntity4Read().id

        // when
        val mathDocsPaperEntity = em.find(MathDocsPaperEntity::class.java, id)

        // then
        assertThat(mathDocsPaperEntity.id).isEqualTo(id)
        assertThat(mathDocsPaperEntity.contentsIdList).contains(2365)
        assertThat(mathDocsPaperEntity.docsGrade).isNotNull
        assertThat(mathDocsPaperEntity.docsTitle).isNotNull
        assertThat(mathDocsPaperEntity.docsSubTitle).isNotNull
        assertThat(mathDocsPaperEntity.docsOwner).isNotNull
        assertThat(mathDocsPaperEntity.docsStts).isEqualTo(DocsStatusType.None)
        assertThat(mathDocsPaperEntity.sysCreateDate).isNotNull()
        assertThat(mathDocsPaperEntity.sysUpdateDate).isNotNull()
        assertThat(mathDocsPaperEntity.sysDeleteDate).isNull()
    }
}