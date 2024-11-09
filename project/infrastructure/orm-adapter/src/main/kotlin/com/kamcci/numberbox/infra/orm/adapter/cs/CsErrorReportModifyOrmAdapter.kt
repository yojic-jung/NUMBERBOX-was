package com.kamcci.numberbox.infra.orm.adapter.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportSaveDto
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.factory.cs.CsErrorReportFactory
import org.springframework.stereotype.Repository

@Repository
class CsErrorReportModifyOrmAdapter : CsErrorReportModifyOrmPort, BaseRepository() {
    override fun create(saveDto: CsErrorReportSaveDto): Long {
        val saveEntity = CsErrorReportFactory.getSaveEntity(saveDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}