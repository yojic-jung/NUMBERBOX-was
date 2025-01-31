package com.kamcci.numberbox.app.service.stub.usecase.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import java.util.*

class MockHwpConvertContentsReadCase : HwpConvertContentsReadCase {
    override fun readAllByMemberId(memberId: UUID): List<HwpConvertContentsVo> {
        TODO("Not yet implemented")
    }
}