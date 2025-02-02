package com.kamcci.numberbox.app.service.stub.port.orm.cs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.vo.docs.MathAllTypeDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo
import com.kamcci.numberbox.app.port.orm.docs.MathDocsReadOrmPort
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathAllTypeDocsVoList
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsVoList
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathIpsiDocsVoList

class MockMathDocsReadOrmPort : MathDocsReadOrmPort {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var executeCnt = 0 // 실행 횟수

    override fun countGroupByUnitAndType(
        unitIdAndTypeId: List<String>,
        contentsClassifyType: ContentsClassifyType,
        quesLv: List<Int>
    ): List<Long> {
        return listOf(1L, 2L)
    }

    override fun readAllInHouseDocsVoBy(
        unitIdAndTypeId: List<String>,
        quesLv: List<Int>,
        countByType: Int,
        limit: Int
    ): List<MathDocsVo> {
        return getMathDocsVoList()
    }

    override fun readDocsByContentsIdList(idList: List<Long>): List<MathAllTypeDocsVo> {
        executeCnt++
        return getMathAllTypeDocsVoList()
    }

    override fun readAllIpsiDocsVoBy(readDto: MathIpsiDocsReadDto): List<MathIpsiDocsVo> {
        executeCnt++
        return getMathIpsiDocsVoList()
    }

    override fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathDocsVo> {
        executeCnt++
        return getMathDocsVoList()
    }
}