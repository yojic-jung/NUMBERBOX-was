package com.kamcci.numberbox.app.port.repository.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType

interface MathDocsReadOrmPort {
    fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long>

    fun findPartitionedByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>,
        countByType: Int, // 유형 별 문제수
        limit: Int,
    ): List<Any>
}