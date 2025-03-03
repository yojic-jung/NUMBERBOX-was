package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathResourceEntity 연관관계 테스트`() {
        // given
        val id = SUCCESS_ID

        // when
        val mathResourceEntity = em.find(MathResourceEntity::class.java, id)

        // then
        assertThat(mathResourceEntity.mathResourceCate[0].id).isOne()
        assertThat(mathResourceEntity.mathResourceImg[0].id).isOne()
    }
}