package com.kamcci.numberbox.app.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.system.construction.TXExecute

/**
 *  hwp to html 변환 컨텐츠
 */
interface HwpConvertContentsWriteCase {
    // 변환 컨텐츠 저장
    @TXExecute
    fun create(createDto: HwpConvertContentsCreateDto): Long
}