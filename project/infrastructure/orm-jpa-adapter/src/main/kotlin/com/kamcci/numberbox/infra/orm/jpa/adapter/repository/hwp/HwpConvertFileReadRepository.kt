package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertFileTypeVo
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileReadCase
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp.QHwpConvertFileEntity.hwpConvertFileEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class HwpConvertFileReadRepository : HwpConvertFileReadCase, BaseRepository() {
    override fun readByRequestAtLoe(requestAt: LocalDateTime): List<HwpConvertFileTypeVo> {
        return queryFactory
            .select(
                Projections.constructor(
                    HwpConvertFileTypeVo::class.java,
                    hwpConvertFileEntity.id,
                    hwpConvertFileEntity.convertType,
                    hwpConvertFileEntity.convertFileName
                )
            )
            .from(hwpConvertFileEntity)
            .where(
                hwpConvertFileEntity.isRequestSuccess.eq(false),
                hwpConvertFileEntity.requestAt.loe(requestAt),
                hwpConvertFileEntity.deletedAt.isNull
            ).fetch()
    }
}
