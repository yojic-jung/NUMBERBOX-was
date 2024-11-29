package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class CsErrorReportReadRepositoryTest(
    @Autowired
    private val csErrorReportReadRepository: CsErrorReportReadRepository
) {
    companion object {
        const val EXIST_ID = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `문의자 id로 조회`() {
        // given
        val memberId = UUID.fromString(EXIST_ID)

        // when
        val csErrReportList = csErrorReportReadRepository.readByMemberId(memberId)

        // then
        assertThat(csErrReportList.size).isGreaterThan(0)
    }
}