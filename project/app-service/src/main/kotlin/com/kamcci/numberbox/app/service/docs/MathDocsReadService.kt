package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathInHouseDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.docs.MathInHouseDocsVo
import com.kamcci.numberbox.app.port.repository.docs.MathDocsReadOrmPort
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadUseCase

@UseCase
class MathDocsReadService(
    private val mathDocsReadOrmPort: MathDocsReadOrmPort
) : MathDocsReadUseCase {

    // 각 유형별로 몇 문제씩 뽑아와야할지 기준
    private val cntStandards: List<Int> = listOf(1, 2, 3, 4, 5, 10, 15, 20, 30, 50, 100)

    // 레벨별 조회 기준 (조회할 레벨 list, 해당 레벨 문제 부족시 추가 조회할 레빌 list)
    val lowLv = Pair(listOf(1, 2), listOf(3, 4))
    val midLv = Pair(listOf(2, 3, 4), listOf(1, 5))
    val highLv = Pair(listOf(4, 5), listOf(2, 3))
    override fun makeInHouseDocs(readDto: MathInHouseDocsReadDto): List<MathInHouseDocsVo> {
        // 레벨 조건
        val lvCond = when (readDto.quesLevel) {
            // 난이도 하 선택한 경우
            1, 2 -> lowLv

            // 난이도 중 선택한 경우
            3, 4 -> midLv

            // 난이도 상 선택한 경우
            5 -> highLv

            else -> throw IllegalArgumentException("난이도는 1부터 5까지만 설정 가능합니다. 사용자 설정 값 : $readDto.quesLevel")
        }

        // 문제 조회
        val unitIdAndTypeId = readDto.unitIdAndTypeId.split(",").map { it.trim() }
        val mainContents = makeDocs(ContentsClassifyType.InHouse, unitIdAndTypeId, lvCond.first, readDto.count)
        if (mainContents.size == readDto.count) {
            return mainContents

        }


        // 문제 부족시 다른 난이도에서 추가
        val subContents =
            makeDocs(
                ContentsClassifyType.InHouse,
                unitIdAndTypeId,
                lvCond.second,
                readDto.count - mainContents.size
            )
        return mainContents + subContents
    }

    private fun makeDocs(
        contentsType: ContentsClassifyType,
        unitIdAndTypeId: List<String>,
        lvCond: List<Int>,
        count: Int
    ): List<MathInHouseDocsVo> {
        // 1. 각 유형별로 몇문씩 뽑아와야 하는지 기준 설정
        val conCntList =
            mathDocsReadOrmPort.countGroupByUnitAndType(unitIdAndTypeId, ContentsClassifyType.InHouse, lvCond)
        var perN = 1 // 유형당 기준
        for (standard in cntStandards) {
            val sum: Int = conCntList.map { conCnt ->
                if (conCnt >= standard) standard else conCnt.toInt()
            }.sum()
            perN = standard

            // 문제 수가 충분하면 해당 기준으로 설정
            if (sum >= count) break
        }

        // 2. 문제 조회
        return mathDocsReadOrmPort.readPartitionedByUnitAndType(
            unitIdAndTypeId,
            contentsType,
            lvCond,
            perN,
            count
        )
    }

    override fun makeIpsiDocs(readDto: MathIpsiDocsReadDto) {
        TODO("Not yet implemented")
    }

    override fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathInHouseDocsVo> =
        mathDocsReadOrmPort.readAdditionalContents(readDto)
}