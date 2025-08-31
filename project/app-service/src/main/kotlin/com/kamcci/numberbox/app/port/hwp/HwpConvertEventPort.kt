package com.kamcci.numberbox.app.port.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent

/**
 * hwp 제작 및 변환 서버에 요청하는 client
 */
interface HwpConvertEventPort {
    /**
     * json to hwp 변환 요청
     */
    fun requestHwp(eventDto: JsonToHwpRequestEvent)

    /**
     * hwp to html 변환 요청
     */
    fun requestHtml(eventDto: HwpToHtmlRequestEvent)
}