package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.HwpConvertContentsEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class HwpConvertContentsWriteRepository : HwpConvertContentsWriteCase, BaseRepository() {

    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        val now = LocalDateTime.now()
        val saveEntity = HwpConvertContentsEntity().apply {
            memberId = createDto.memberId
            isConverted = createDto.isConverted
            filePath = createDto.filePath
            contents = createDto.contents
            imgPath = createDto.imgPath
            isGrammarConverted = createDto.isGrammarConverted
            memberId = createDto.memberId
            sysCreateDate = now
            sysUpdateDate = now
        }

        em.persist(saveEntity)
        return saveEntity.id!!
    }
}