package com.kamcci.numberbox.app.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo

/**
 * 학습지 제작
 */
interface MathDocsReadUseCase {

    // 수학문제 학습지 제작
    fun makeDocs(readDto: MathDocsReadDto): List<MathDocsVo>

    // 나의 학습지 조회 - 학습지 id로
    fun readDocsByDocsPaperId(contentsIdList: List<Long>): List<MathDocsVo>

    // 입시 수학문제 제작
    fun makeIpsiDocs(readDto: MathIpsiDocsReadDto): List<MathIpsiDocsVo>

    // 추가 문제 조회
    fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathDocsVo>


}