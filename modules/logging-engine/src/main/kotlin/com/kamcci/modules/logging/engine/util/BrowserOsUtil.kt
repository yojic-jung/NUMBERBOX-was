package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.BrowserType
import com.kamcci.modules.logging.control.constant.OsType

object BrowserOsUtil {
    fun browserLogging(browser: String?, osType: OsType): BrowserType {
        if (browser == null) return BrowserType.ETC

        val browserLowercase = browser.lowercase()
        return if (osType == OsType.WINDOW) {
            when {
                browserLowercase.contains(BrowserType.SAFARI.attrName) -> BrowserType.ETC
                else -> BrowserType.entries.find { browserLowercase.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else if (osType == OsType.MAC) {
            when {
                // mac chrom의 경우 safari도 포함하기에 safari 먼저 체크해야함
                !browserLowercase.contains(BrowserType.CHROME.attrName) && browserLowercase.contains(BrowserType.SAFARI.attrName) -> BrowserType.SAFARI
                browserLowercase.contains(BrowserType.CHROME.attrName) -> BrowserType.CHROME
                else -> BrowserType.entries.find { browserLowercase.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else {
            BrowserType.ETC
        }
    }

    fun osLogging(os: String?): OsType {
        if (os == null) return OsType.ETC

        // os
        val osLowercase = os.lowercase()
        return OsType.entries.find { osLowercase.contains(it.attrName) } ?: OsType.ETC
    }
}