package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.cs.CsErrorReportDummyFactory.getCsErrorReportDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class CsErrorReportReadRepositoryTest(
    @Autowired
    private val csErrorReportReadRepository: CsErrorReportReadRepository
) {

    @Test
    fun `문의자 id로 조회`() {
        // given
        val dummyEntity = getCsErrorReportDummyEntity()
        val memberId = dummyEntity.memberId

        // when
        val csErrReportList = csErrorReportReadRepository.readByMemberId(memberId)

        // then
        assertThat(csErrReportList[0].id).isEqualTo(dummyEntity.id)
    }
}