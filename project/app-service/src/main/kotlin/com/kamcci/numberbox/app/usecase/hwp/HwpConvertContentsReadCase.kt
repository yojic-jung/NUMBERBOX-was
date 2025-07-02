package com.kamcci.numberbox.app.usecase.hwp

import com.kamcci.numberbox.app.domain.vo.hwp.HwpConvertContentsVo
import java.util.*

/**
 *  hwp to html 변환 컨텐츠 조회
 */
interface HwpConvertContentsReadCase {
    // 변환 컨텐츠 조회
    fun readAllByMemberId(memberId: UUID): List<HwpConvertContentsVo>
}