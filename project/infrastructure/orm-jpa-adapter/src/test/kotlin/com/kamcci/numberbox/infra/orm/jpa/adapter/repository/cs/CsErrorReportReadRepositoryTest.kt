package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.cs.CsErrorReportEntityDummy.CS_ERR_REPORT_MEMBER_ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class CsErrorReportReadRepositoryTest(
    @Autowired
    private val csErrorReportReadRepository: CsErrorReportReadRepository
) {

    @Test
    fun `문의자 id로 조회`() {
        // given
        val existId = UUID.fromString(CS_ERR_REPORT_MEMBER_ID)

        // when
        val csErrReportList = csErrorReportReadRepository.readByMemberId(existId)

        // then
        assertThat(csErrReportList).isNotEmpty
    }
}