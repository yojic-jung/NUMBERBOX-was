package com.kamcci.numberbox.restapi.stub.hwp

import com.kamcci.numberbox.restapi.util.hwp.HwpConvertFileUtil

class MockHwpConvertFileUtil : HwpConvertFileUtil() {
    override fun unzip(zipBytes: ByteArray): Pair<String?, Map<String, ByteArray>> {
        return Pair("index.xhtml", mutableMapOf("BIN001.png" to "".toByteArray()))
    }
}