package com.kamcci.numberbox.infra.orm.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcCreateDto
import com.kamcci.numberbox.infra.orm.entity.math.MathContentsIpsiSrcEntity
import java.time.LocalDateTime

object MathContentsIpsiFactory {
    fun getSaveEntity(
        sourceDto: MathConIpsiSrcCreateDto
    ): MathContentsIpsiSrcEntity {
        val now = LocalDateTime.now()
        val ipsiEntity = MathContentsIpsiSrcEntity()
            .apply {
                manageIns = sourceDto.manageIns
                impYear = sourceDto.impYear
                impMonth = sourceDto.impMonth
                wrongRatio = sourceDto.wrongRatio
                paperType = sourceDto.paperType
                oddQuesNum = sourceDto.oddQuesNum
                evenQuesNum = sourceDto.evenQuesNum
                sysCreateDate = now
                sysUpdateDate = now
            }

        return ipsiEntity
    }

}