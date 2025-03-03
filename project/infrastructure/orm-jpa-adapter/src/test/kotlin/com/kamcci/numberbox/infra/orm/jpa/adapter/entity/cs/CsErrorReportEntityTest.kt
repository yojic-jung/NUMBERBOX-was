package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.cs.CsErrorReportDummyFactory.getCsErrorReportAllValueDummyEntity
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
        val allValDummyEntity = getCsErrorReportAllValueDummyEntity()
        val id = allValDummyEntity.id

        // when
        val csErrorReportEntity = em.find(CsErrorReportEntity::class.java, id)

        // then
        assertEntityProperty(csErrorReportEntity, id)
    }

    private fun assertEntityProperty(csErrorReportEntity: CsErrorReportEntity, id: Long) {
        Assertions.assertThat(csErrorReportEntity.id).isEqualTo(id)
        Assertions.assertThat(csErrorReportEntity.errType).isEqualTo(CSErrorType.Etc)
        Assertions.assertThat(csErrorReportEntity.contentsId).isZero()
        Assertions.assertThat(csErrorReportEntity.reportContents).isNotNull()
        Assertions.assertThat(csErrorReportEntity.replyMemberId).isNotNull()
        Assertions.assertThat(csErrorReportEntity.replyContents).isNotNull()
        Assertions.assertThat(csErrorReportEntity.clientOs).isEqualTo(OsType.Windows)
        Assertions.assertThat(csErrorReportEntity.clientBrowser).isEqualTo(BrowserType.Chrome)
        Assertions.assertThat(csErrorReportEntity.firstImgPath).isNotNull()
        Assertions.assertThat(csErrorReportEntity.firstImgName).isNotNull()
        Assertions.assertThat(csErrorReportEntity.secondImgPath).isNotNull()
        Assertions.assertThat(csErrorReportEntity.secondImgName).isNotNull()
        Assertions.assertThat(csErrorReportEntity.thirdImgPath).isNotNull()
        Assertions.assertThat(csErrorReportEntity.thirdImgName).isNotNull()
        Assertions.assertThat(csErrorReportEntity.reportStts).isEqualTo(ReportSttsType.Submit)
        Assertions.assertThat(csErrorReportEntity.sysUpdateDate).isNotNull()
        Assertions.assertThat(csErrorReportEntity.sysCreateDate).isNotNull()
    }
}