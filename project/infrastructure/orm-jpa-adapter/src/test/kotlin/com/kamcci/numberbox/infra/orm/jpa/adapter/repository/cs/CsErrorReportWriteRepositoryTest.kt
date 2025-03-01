package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.app.service.sample.CsSampleData.getCsErrorReportCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class CsErrorReportWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val csErrorReportWriteRepository: CsErrorReportWriteRepository
) {

    @Test
    fun `고객센터 문의 - 영속화`() {
        // given
        val csErrorReportCreateDto = getCsErrorReportCreateDto()

        // when
        val id = csErrorReportWriteRepository.create(csErrorReportCreateDto)
        em.flush()
        em.clear()

        // then
        Assertions.assertThat(id).isPositive()
    }
}