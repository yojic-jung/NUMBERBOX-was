package com.kamcci.numberbox.app.domain.vo.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType

data class HwpConvertFileTypeVo(
    // hwpConvertFile.id
    val id: Long,
    // 파일 변환 완료 여부
    val convertType: HwpConvertFileType,
    // json 형식 파일 : 문자, 수식문법, binary 문자열 이미지 포함
    val fileName: String,
)