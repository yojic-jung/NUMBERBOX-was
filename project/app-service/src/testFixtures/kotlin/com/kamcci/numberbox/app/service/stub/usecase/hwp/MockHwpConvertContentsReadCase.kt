package com.kamcci.numberbox.app.service.stub.usecase.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import java.time.LocalDateTime
import java.util.*

class MockHwpConvertContentsReadCase : HwpConvertContentsReadCase {
    override fun readAllByMemberId(memberId: UUID): List<HwpConvertContentsVo> {
        return listOf(
            HwpConvertContentsVo(
                id = 1L,
                fileName = "",
                contents = "",
                isConverted = false,
                imgPath = "",
                sysCreateDate = LocalDateTime.now(),
                sysUpdateDate = LocalDateTime.now(),
            )
        )
    }
}