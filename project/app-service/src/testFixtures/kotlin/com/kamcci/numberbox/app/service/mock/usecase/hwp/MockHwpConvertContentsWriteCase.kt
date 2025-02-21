package com.kamcci.numberbox.app.service.mock.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_STRING
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import java.util.*

class MockHwpConvertContentsWriteCase : HwpConvertContentsWriteCase {
    override fun create(createDto: HwpConvertContentsCreateDto): Long {
        return if (createDto.fileName != FAIL_STRING) 0L else 1L
    }

    override fun update(updateDto: HwpConvertContentsUpdateDto): Long {
        return when {
            updateDto.id == FAIL_ID -> 0L
            updateDto.id == EXCEPTION_ID -> throw RuntimeException(STUB_EXCEPTION_MSG)
            else -> 1L
        }
    }

    override fun delete(contentsId: Long, memberId: UUID): Long {
        return when {
            contentsId == FAIL_ID -> 0L
            contentsId == EXCEPTION_ID -> throw RuntimeException(STUB_EXCEPTION_MSG)
            else -> 1L
        }
    }
}