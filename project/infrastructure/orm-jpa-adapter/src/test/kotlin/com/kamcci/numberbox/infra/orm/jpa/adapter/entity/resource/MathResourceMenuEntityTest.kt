package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceMenuEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathResourceMenuEntity 조회 테스트`() {
        // given
        val id = 1L

        // when
        val mathResourceMenuEntity = em.find(MathResourceMenuEntity::class.java, id)

        // then
        Assertions.assertThat(mathResourceMenuEntity.id).isEqualTo(id)
        Assertions.assertThat(mathResourceMenuEntity.mainCateId).isOne()
        Assertions.assertThat(mathResourceMenuEntity.mainCateName).isNotNull()
        Assertions.assertThat(mathResourceMenuEntity.midCateId).isOne()
        Assertions.assertThat(mathResourceMenuEntity.midCateName).isNotNull()
        Assertions.assertThat(mathResourceMenuEntity.alignOrder).isOne()
    }
}