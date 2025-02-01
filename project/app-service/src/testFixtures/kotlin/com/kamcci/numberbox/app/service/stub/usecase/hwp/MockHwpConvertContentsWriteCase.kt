package com.kamcci.numberbox.app.service.stub.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.service.constant.FailConstant
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_STRING
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import java.util.*

class MockHwpConvertContentsWriteCase : HwpConvertContentsWriteCase {
    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        return if (createDto.fileName != FAIL_STRING) 0L else 1L
    }

    override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
        return if (updateDto.id == FailConstant.FAIL_ID) 0L else 1L
    }

    override fun delete(contentsId: Long, memberId: UUID): Long {
        return if (contentsId == FailConstant.FAIL_ID) 0L else 1L
    }
}