package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.HwpConvertContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.QHwpConvertContentsEntity.hwpConvertContentsEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class HwpConvertContentsWriteRepository : HwpConvertContentsWriteCase, BaseRepository() {
    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        val now = LocalDateTime.now()
        val saveEntity = HwpConvertContentsEntity().apply {
            memberId = createDto.memberId
            isConverted = createDto.isConverted
            fileName = createDto.fileName
            contents = createDto.contents
            imgPath = createDto.imgPath
            isGrammarConverted = false
            memberId = createDto.memberId
            sysCreateDate = now
            sysUpdateDate = now
        }

        em.persist(saveEntity)
        return saveEntity.id!!
    }

    override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
        return queryFactory
            .update(hwpConvertContentsEntity)
            .set(hwpConvertContentsEntity.contents, updateDto.contents)
            .set(hwpConvertContentsEntity.isGrammarConverted, updateDto.isGrammarConverted)
            .set(hwpConvertContentsEntity.sysUpdateDate, LocalDateTime.now())
            .where(
                hwpConvertContentsEntity.id.eq(updateDto.id),
                hwpConvertContentsEntity.memberId.eq(updateDto.memberId)
            )
            .execute()
    }

    override fun delete(contentsId: Long, memberId: UUID): Long {
        return queryFactory
            .delete(hwpConvertContentsEntity)
            .where(
                hwpConvertContentsEntity.id.eq(contentsId),
                hwpConvertContentsEntity.memberId.eq(memberId)
            )
            .execute()
    }
}