package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.BrowserType
import com.kamcci.modules.logging.control.constant.OsType

object BrowserOsUtil {
    // 브라우저 정보 로깅 - os 버전에 따라 추출 문자열 다르기에 분기 처리
    fun browserLogging(browser: String?, osType: OsType): BrowserType {
        if (browser == null) return BrowserType.ETC

        val browserLowercase = browser.lowercase()
        return if (osType == OsType.WINDOW) {
            when {
                browserLowercase.contains(BrowserType.SAFARI.attrName) -> BrowserType.SAFARI
                else -> BrowserType.entries.find { browserLowercase.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else if (osType == OsType.MAC) {
            when {
                // mac chrome의 경우 safari도 포함하기에 safari 먼저 체크해야함
                !browserLowercase.contains(BrowserType.CHROME.attrName) && browserLowercase.contains(BrowserType.SAFARI.attrName) -> BrowserType.SAFARI
                browserLowercase.contains(BrowserType.CHROME.attrName) -> BrowserType.CHROME
                else -> BrowserType.entries.find { browserLowercase.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else {
            BrowserType.ETC
        }
    }

    // os 정보 추출
    fun osLogging(os: String?): OsType {
        if (os == null) return OsType.ETC

        // os
        val osLowercase = os.lowercase()
        return OsType.entries.find { osLowercase.contains(it.attrName) } ?: OsType.ETC
    }
}