package com.kamcci.numberbox.app.domain.dto.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import java.util.*

/**
 * 파일 변환 요청 정보 기록 dto
 */
data class HwpConvertFileCreateDto(
    val memberId: UUID,
    // 요청 타입
    val convertType: HwpConvertFileType,
    // 원본 파일명
    var originFileName: String,
) {
    companion object {
        const val INVALID_FILE_NAME = "변환 요청하는 원본 파일명을 적어주세요."
    }

    init {
        require(originFileName.isNotEmpty()) { INVALID_FILE_NAME }
    }
}
