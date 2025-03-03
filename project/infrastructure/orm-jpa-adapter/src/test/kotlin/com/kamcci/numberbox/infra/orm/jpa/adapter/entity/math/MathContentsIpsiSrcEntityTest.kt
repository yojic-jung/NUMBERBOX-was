package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsIpsiSrcEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathContentsIpsiSrcEntity 연관관계 테스트`() {
        // given
        val id = SUCCESS_ID

        // when
        val mathContentsIpsiSrcEntity = em.find(MathContentsIpsiSrcEntity::class.java, id)

        // then
        Assertions.assertThat(mathContentsIpsiSrcEntity.id).isEqualTo(id)
        Assertions.assertThat(mathContentsIpsiSrcEntity.mathContents?.id).isEqualTo(4907)
    }
}