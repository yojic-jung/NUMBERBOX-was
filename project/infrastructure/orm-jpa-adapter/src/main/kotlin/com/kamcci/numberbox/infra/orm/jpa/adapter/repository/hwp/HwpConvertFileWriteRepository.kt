package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileWriteCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.HwpConvertFileEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class HwpConvertFileWriteRepository: HwpConvertFileWriteCase, BaseRepository() {
    override fun create(createDto: HwpConvertFileCreateDto): Long {
        val now = LocalDateTime.now()
        val saveEntity = HwpConvertFileEntity().apply {
            memberId = createDto.memberId
            convertType = createDto.convertType
            originFileName = createDto.originFileName
            requestAt = now
        }

        em.persist(saveEntity)
        return saveEntity.id
    }
}