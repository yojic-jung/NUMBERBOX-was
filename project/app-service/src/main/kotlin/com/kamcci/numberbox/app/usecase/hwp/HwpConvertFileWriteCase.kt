package com.kamcci.numberbox.app.usecase.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.domain.system.construction.TXExecute

/**
 *  hwp to html 변환 파일 조회
 */
interface HwpConvertFileWriteCase {
    // 변환 요청 정보 저장
    @TXExecute
    fun create(createDto: HwpConvertFileCreateDto): Long
}