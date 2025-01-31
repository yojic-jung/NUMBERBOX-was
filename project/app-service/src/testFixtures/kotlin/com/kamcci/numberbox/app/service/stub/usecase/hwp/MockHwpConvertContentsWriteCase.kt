package com.kamcci.numberbox.app.service.stub.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import java.util.*

class MockHwpConvertContentsWriteCase : HwpConvertContentsWriteCase {
    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        return 1L
    }

    override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
        return 1L
    }

    override fun delete(contentsId: Long, memberId: UUID): Long {
        return 1L
    }
}