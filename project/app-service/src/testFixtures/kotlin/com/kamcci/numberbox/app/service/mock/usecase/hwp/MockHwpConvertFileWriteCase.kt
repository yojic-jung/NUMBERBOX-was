package com.kamcci.numberbox.app.service.mock.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileWriteCase

class MockHwpConvertFileWriteCase : HwpConvertFileWriteCase {
    override fun create(createDto: HwpConvertFileCreateDto): Long {
        return 1L
    }
}