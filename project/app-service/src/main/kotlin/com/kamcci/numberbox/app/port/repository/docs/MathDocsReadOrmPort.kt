package com.kamcci.numberbox.app.port.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.vo.docs.MathInHouseDocsVo

interface MathDocsReadOrmPort {
    fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long>

    fun readPartitionedByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>,
        countByType: Int, // 유형 별 문제수
        limit: Int,
    ): List<MathInHouseDocsVo>

    fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathInHouseDocsVo>
}