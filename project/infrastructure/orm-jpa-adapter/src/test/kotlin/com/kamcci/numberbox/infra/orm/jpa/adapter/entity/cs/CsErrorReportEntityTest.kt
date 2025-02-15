package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class CsErrorReportEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `CsErrorReportEntity 조회`() {
        // given
        val id = 1L

        // when
        val csErrorReportEntity = em.find(CsErrorReportEntity::class.java, id)

        // then
        Assertions.assertThat(csErrorReportEntity.id).isEqualTo(id)
        Assertions.assertThat(csErrorReportEntity.errType).isEqualTo(CSErrorType.Etc)
        Assertions.assertThat(csErrorReportEntity.contentsId).isZero()
        Assertions.assertThat(csErrorReportEntity.reportContents).isEqualTo("문의 사항 운영 테스트")
        Assertions.assertThat(csErrorReportEntity.replyMemberId).isNull()
        Assertions.assertThat(csErrorReportEntity.replyContents).isNull()
        Assertions.assertThat(csErrorReportEntity.clientOs).isEqualTo(OsType.Windows)
        Assertions.assertThat(csErrorReportEntity.clientBrowser).isEqualTo(BrowserType.Chrome)
        Assertions.assertThat(csErrorReportEntity.firstImgPath).isNull()
        Assertions.assertThat(csErrorReportEntity.firstImgName).isNull()
        Assertions.assertThat(csErrorReportEntity.secondImgPath).isNull()
        Assertions.assertThat(csErrorReportEntity.secondImgName).isNull()
        Assertions.assertThat(csErrorReportEntity.thirdImgPath).isNull()
        Assertions.assertThat(csErrorReportEntity.thirdImgName).isNull()
        Assertions.assertThat(csErrorReportEntity.reportStts).isEqualTo(ReportSttsType.Submit)
        Assertions.assertThat(csErrorReportEntity.sysUpdateDate).isNotNull()
        Assertions.assertThat(csErrorReportEntity.sysCreateDate).isNotNull()
    }
}