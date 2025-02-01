package com.kamcci.numberbox.app.service.stub.port.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import java.io.InputStream

class MockHwpSocketClient : HwpSocketClient {
    override fun requestHwpFile(jsonMsg: String): ByteArray {
        // 필요한 로직을 작성합니다. 예시로 빈 바이트 배열을 반환합니다.
        return byteArrayOf()
    }

    override fun requestHtmlZip(hwpFileIS: InputStream, dataSize: Int, extension: HwpExtensionType): ByteArray {
        // 필요한 로직을 작성합니다. 예시로 빈 바이트 배열을 반환합니다.
        return byteArrayOf()
    }
}