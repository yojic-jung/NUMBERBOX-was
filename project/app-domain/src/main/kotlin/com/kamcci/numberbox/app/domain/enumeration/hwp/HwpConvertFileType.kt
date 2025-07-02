package com.kamcci.numberbox.app.domain.enumeration.hwp

/**
 * 파일 변환 요청 타입
 */
enum class HwpConvertFileType(val dbData: String, val desc: String) {
    JsonToHwp("jsonToHwp", "json 문자열 to hwp 파일 변환 요청"),
    HwpToHtml("hwpToHtml", "hwp 파일 to HTML 문서 변환 요청")
}