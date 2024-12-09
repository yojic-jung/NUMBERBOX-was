package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
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
        val id = 1L

        // when
        val mathDocsPaperEntity = em.find(MathDocsPaperEntity::class.java, id)

        // then
        assertThat(mathDocsPaperEntity.id).isEqualTo(id)
        assertThat(mathDocsPaperEntity.contentsIdList).contains(2365)
        assertThat(mathDocsPaperEntity.docsGrade).isEqualTo("중1")
        assertThat(mathDocsPaperEntity.docsTitle).isEqualTo("중등 1-2 학습지")
        assertThat(mathDocsPaperEntity.docsSubTitle).isEqualTo("기본도형 ~ 자료의 정리와 해석")
        assertThat(mathDocsPaperEntity.docsOwner).isEqualTo("최신")
        assertThat(mathDocsPaperEntity.docsStts).isEqualTo(DocsStatusType.None)
        assertThat(mathDocsPaperEntity.sysCreateDate).isNotNull()
        assertThat(mathDocsPaperEntity.sysUpdateDate).isNotNull()
        assertThat(mathDocsPaperEntity.sysDeleteDate).isNull()
    }
}