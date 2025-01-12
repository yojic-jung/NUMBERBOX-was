package com.kamcci.numberbox.app.service.hwp

import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.usecase.hwp.HwpFileConvertCase

@UseCase
class HwpFileConvertService(
    private val hwpSocketClient: HwpSocketClient
) : HwpFileConvertCase {
    override fun convertJsonMsgToHwp(jsonMsg: String): ByteArray =
        hwpSocketClient.requestHwpFile(jsonMsg)
}