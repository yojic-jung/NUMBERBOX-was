package com.kamcci.numberbox.app.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo

interface MathDocsReadOrmPort {
    fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long>

    fun readAllInHouseDocsVoBy(
        unitIdAndTypeId: List<String>,
        quesLv: List<Int>,
        countByType: Int, // 유형 별 문제수
        limit: Int,
    ): List<MathDocsVo>

    // 수학문제 id로 조회
    fun readDocsByContentsIdList(idList: List<Long>): List<MathDocsVo>

    fun readAllIpsiDocsVoBy(readDto: MathIpsiDocsReadDto): List<MathIpsiDocsVo>

    fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathDocsVo>
}