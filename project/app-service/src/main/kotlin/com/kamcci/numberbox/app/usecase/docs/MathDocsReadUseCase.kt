package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathInHouseDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.vo.docs.MathInHouseDocsVo

/**
 * 학습지 제작
 */
interface MathDocsReadUseCase {

    // 자체제작 수학문제 학습지 제작
    fun makeInHouseDocs(readDto: MathInHouseDocsReadDto): List<MathInHouseDocsVo>

    // 입시 수학문제 제작
    fun makeIpsiDocs(readDto: MathIpsiDocsReadDto)

    // 추가 문제 조회
    fun findAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathInHouseDocsVo>

}