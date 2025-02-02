package com.kamcci.numberbox.app.service.stub.port.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import java.io.InputStream

class MockHwpSocketClient : HwpSocketClient {
    override fun requestHwpFile(jsonMsg: String): ByteArray {
        return byteArrayOf()
    }

    override fun requestHtmlZip(hwpFileIS: InputStream, dataSize: Int, extension: HwpExtensionType): ByteArray {
        return byteArrayOf()
    }
}