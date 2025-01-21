package com.kamcci.numberbox.app.port.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import java.io.InputStream

/**
 * hwp 제작 및 변환 서버에 요청하는 client
 */
interface HwpSocketClient {
    // json 형식(문자, 수식문법, binary 문자열 이미지 포함)으로 이루어진 문자열을 hwp로 반환
    fun requestHwpFile(jsonMsg: String): ByteArray

    //  hwp 파일을 html로 변환 요청 (문자, 수식문법, 이미지가 포함된 html) - 2GB 이하 파일만 가능
    fun requestHtmlZip(hwpFileIS: InputStream, dataSize: Int, extension: HwpExtensionType): ByteArray
}