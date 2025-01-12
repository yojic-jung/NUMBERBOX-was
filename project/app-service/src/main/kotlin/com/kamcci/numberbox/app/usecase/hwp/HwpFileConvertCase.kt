package com.kamcci.numberbox.app.usecase.hwp


/**
 * hwp 제작 및 변환 서버에 요청하는 client
 */
interface HwpFileConvertCase {
    // json 형식(문자, 수식문법, binary 문자열 이미지 포함)으로 이루어진 문자열을 hwp로 반환
    fun convertJsonMsgToHwp(jsonMsg: String): ByteArray
}