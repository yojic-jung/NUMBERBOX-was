package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceImgDummyFactory.getMathResourceImgDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceImgEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathResourceImgEntity 조회 테스트`() {
        // given
        val id = getMathResourceImgDummyEntity().id

        // when
        val mathResourceImgEntity = em.find(MathResourceImgEntity::class.java, id)

        // then
        Assertions.assertThat(mathResourceImgEntity.id).isEqualTo(id)
    }
}