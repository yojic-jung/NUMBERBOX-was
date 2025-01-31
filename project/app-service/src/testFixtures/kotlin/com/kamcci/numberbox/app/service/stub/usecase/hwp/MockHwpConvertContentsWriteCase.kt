package com.kamcci.numberbox.app.service.stub.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import java.util.*

class MockHwpConvertContentsWriteCase : HwpConvertContentsWriteCase {
    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        return if (createDto.fileName != "실패") 0L else 1L
    }

    override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
        return if (updateDto.id % 2L == 1L) 1L else 0L
    }

    override fun delete(contentsId: Long, memberId: UUID): Long {
        return if (contentsId % 2L == 1L) 1L else 0L
    }
}