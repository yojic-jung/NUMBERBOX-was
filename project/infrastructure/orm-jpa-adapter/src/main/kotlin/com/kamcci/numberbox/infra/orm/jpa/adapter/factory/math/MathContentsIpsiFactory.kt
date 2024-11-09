package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsIpsiSrcEntity
import java.time.LocalDateTime

object MathContentsIpsiFactory {
    fun getSaveEntity(
        sourceDto: MathConIpsiSrcModifyDto
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

    fun getUpdateEntity(
        orgEntity: MathContentsIpsiSrcEntity,
        sourceDto: MathConIpsiSrcModifyDto
    ): MathContentsIpsiSrcEntity {
        val ipsiEntity = orgEntity
            .apply {
                manageIns = sourceDto.manageIns
                impYear = sourceDto.impYear
                impMonth = sourceDto.impMonth
                wrongRatio = sourceDto.wrongRatio
                paperType = sourceDto.paperType
                oddQuesNum = sourceDto.oddQuesNum
                evenQuesNum = sourceDto.evenQuesNum
                sysUpdateDate = LocalDateTime.now()
            }

        return ipsiEntity
    }

}