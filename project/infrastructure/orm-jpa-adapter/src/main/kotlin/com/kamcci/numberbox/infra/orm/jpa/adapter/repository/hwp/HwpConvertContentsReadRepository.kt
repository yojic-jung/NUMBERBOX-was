package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.QHwpConvertContentsEntity.hwpConvertContentsEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class HwpConvertContentsReadRepository : HwpConvertContentsReadCase, BaseRepository() {
    override fun readAllByMemberId(memberId: UUID): List<HwpConvertContentsVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    HwpConvertContentsVo::class.java,
                    hwpConvertContentsEntity.id,
                    hwpConvertContentsEntity.memberId,
                    hwpConvertContentsEntity.filePath,
                    hwpConvertContentsEntity.contents,
                    hwpConvertContentsEntity.imgPath,
                    hwpConvertContentsEntity.sysCreateDate,
                    hwpConvertContentsEntity.sysUpdateDate,
                )
            )
            .from(hwpConvertContentsEntity)
            .where(hwpConvertContentsEntity.memberId.eq(memberId))
            .orderBy(hwpConvertContentsEntity.id.desc())
            .fetch()
    }
}