package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathDocsUsageWriteRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mathDocsUsageWriteRepository: MathDocsUsageWriteRepository
) {
    @Test
    fun `학습지 사용 집계 자료 영속화`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val usageDto = MathDocsUsageCreateDto(listOf(1L, 2L, 3L), "", "", "", "")

        // when
        val id = mathDocsUsageWriteRepository.create(memberId, usageDto)
        em.flush()
        em.clear()

        assertThat(id).isGreaterThan(0)
    }
}