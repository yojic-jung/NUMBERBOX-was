package com.kamcci.numberbox.app.service.mock.port.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort

class MockHwpConvertEventAdapter : HwpConvertEventPort {
    override fun requestHwp(eventDto: JsonToHwpRequestEvent) {
    }

    override fun requestHtml(eventDto: HwpToHtmlRequestEvent) {
    }
}