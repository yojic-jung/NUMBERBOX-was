package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp.HwpConvertFileWriteRepository

class MockHwpConvertFileWriteRepository : HwpConvertFileWriteRepository() {

    var executeCnt = 0

    override fun create(createDto: HwpConvertFileCreateDto): Long {
        executeCnt++
        return 1L
    }

    override fun update(id: Long, convertFileName: String): Long {
        executeCnt++
        return 1L
    }
}