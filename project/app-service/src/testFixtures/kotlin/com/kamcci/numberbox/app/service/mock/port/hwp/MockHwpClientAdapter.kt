package com.kamcci.numberbox.app.service.mock.port.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpClientPort
import java.io.InputStream

class MockHwpClientAdapter : HwpClientPort {
    override fun requestHwpFile(jsonMsg: String): ByteArray {
        return byteArrayOf()
    }

    override fun requestHtmlZip(hwpFileIS: InputStream, dataSize: Int, extension: HwpExtensionType): ByteArray {
        return byteArrayOf()
    }
}