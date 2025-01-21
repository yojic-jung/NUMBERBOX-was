package com.kamcci.numberbox.app.domain.enumeration.hwp

/**
 * hwp 서버 요청하는 파일 변환 요청 코드
 */
enum class HwpRequestType(val type: String, val desc: String) {
    HwpToHTML("hwp", "hwp 파일을 문자열과 html 형식으로 변환 요청"), // 수식을 tex문법의 문자열로 변환함
    JsonToHwp("json", "json 문자열을 hwp 파일로 변환 요청"), // json 형식에는 문자열, 수식문법, 이미지가 포함된
}