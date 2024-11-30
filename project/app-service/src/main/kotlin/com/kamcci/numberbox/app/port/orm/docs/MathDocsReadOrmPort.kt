package com.kamcci.numberbox.app.port.orm.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.vo.docs.MathAllTypeDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo

/**
 * 학습지 조회
 */
interface MathDocsReadOrmPort {
    // 단원 유형별 문제수
    fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long>

    // 자체제작 학습지 제작 - 조회
    fun readAllInHouseDocsVoBy(
        unitIdAndTypeId: List<String>,
        quesLv: List<Int>,
        countByType: Int, // 유형 별 문제수
        limit: Int,
    ): List<MathDocsVo>

    // 수학문제 id로 학습지 제작
    fun readDocsByContentsIdList(idList: List<Long>): List<MathAllTypeDocsVo>

    // 입시수학 학습지 제작 - 조회
    fun readAllIpsiDocsVoBy(readDto: MathIpsiDocsReadDto): List<MathIpsiDocsVo>

    // 학습지 추가 문제 - 조회
    fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathDocsVo>
}