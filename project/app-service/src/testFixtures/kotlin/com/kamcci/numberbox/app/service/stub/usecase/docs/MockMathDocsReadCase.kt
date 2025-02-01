package com.kamcci.numberbox.app.service.stub.usecase.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.vo.docs.MathAllTypeDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathDocsVo
import com.kamcci.numberbox.app.domain.vo.docs.MathIpsiDocsVo
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathAllTypeDocsVoList
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathDocsVoList
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathIpsiDocsVoList
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase

class MockMathDocsReadCase : MathDocsReadCase {
    override fun makeDocs(readDto: MathDocsReadDto): List<MathDocsVo> {
        return getMathDocsVoList()
    }

    override fun readDocsByDocsPaperId(contentsIdList: List<Long>): List<MathAllTypeDocsVo> {
        return getMathAllTypeDocsVoList()
    }

    override fun readIpsiDocs(readDto: MathIpsiDocsReadDto): List<MathIpsiDocsVo> {
        return getMathIpsiDocsVoList()
    }

    override fun readAdditionalContents(readDto: MathDocsAdditionalReadDto): List<MathDocsVo> {
        return getMathDocsVoList()
    }
}