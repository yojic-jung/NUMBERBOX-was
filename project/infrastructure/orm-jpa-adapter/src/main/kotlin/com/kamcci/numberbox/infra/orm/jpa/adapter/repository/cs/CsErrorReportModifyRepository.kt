package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.cs

import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.app.port.orm.cs.CsErrorReportModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.cs.CsErrorReportFactory
import org.springframework.stereotype.Repository

@Repository
class CsErrorReportModifyRepository : CsErrorReportModifyOrmPort, BaseRepository() {
    override fun create(createDto: CsErrorReportCreateDto): Long {
        val saveEntity = CsErrorReportFactory.getSaveEntity(createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}