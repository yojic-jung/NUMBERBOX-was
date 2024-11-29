package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class CsErrorReportWriteRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val csErrorReportWriteRepository: CsErrorReportWriteRepository
) {

    @Test
    fun `고객센터 문의 - 영속화`() {
        // given
        val csErrorReportCreateDto = CsErrorReportCreateDto(
            CSErrorType.Etc,
            1L,
            UUID.randomUUID(),
            "",
            OsType.Etc,
            BrowserType.Etc,
            "",
            "",
            "",
            "",
            "",
            "",
            ReportSttsType.Submit
        )

        // when
        val id = csErrorReportWriteRepository.create(csErrorReportCreateDto)
        em.flush()
        em.clear()

        // then
        Assertions.assertThat(id).isGreaterThan(0)
    }
}