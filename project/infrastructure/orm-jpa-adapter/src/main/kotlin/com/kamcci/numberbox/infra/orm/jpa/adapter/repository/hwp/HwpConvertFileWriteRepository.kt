package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileWriteCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.HwpConvertFileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.QHwpConvertFileEntity.hwpConvertFileEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class HwpConvertFileWriteRepository : HwpConvertFileWriteCase, BaseRepository() {
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

    override fun updateIsRequestSuccess(id: Long, isSuccess: Boolean): Long {
        return queryFactory
            .update(hwpConvertFileEntity)
            .set(hwpConvertFileEntity.isRequestSuccess, isSuccess)
            .where(hwpConvertFileEntity.id.eq(id))
            .execute()
    }

    fun update(id: Long, convertFileName: String): Long {
        return queryFactory
            .update(hwpConvertFileEntity)
            .set(hwpConvertFileEntity.convertFileName, convertFileName)
            .set(hwpConvertFileEntity.convertAt, LocalDateTime.now())
            .where(hwpConvertFileEntity.id.eq(id))
            .execute()
    }
}
