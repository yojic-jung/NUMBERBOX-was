package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.BrowserType
import com.kamcci.modules.logging.control.constant.OsType
import java.util.*

object BrowserOsUtil {
    fun browserLogging(browser: String?, os: String?): BrowserType {
        if (browser == null) return BrowserType.ETC

        val osType = osLogging(os)
        return if (osType == OsType.WINDOW) {
            when {
                browser.contains(BrowserType.SAFARI.attrName) -> BrowserType.ETC
                else -> BrowserType.entries.find { browser.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else if (osType == OsType.MAC) {
            when {
                // mac chrom의 경우 safari도 포함하기에 safari 먼저 체크해야함
                !browser.contains(BrowserType.CHROME.attrName) && browser.contains(BrowserType.SAFARI.attrName) -> BrowserType.SAFARI
                browser.contains(BrowserType.CHROME.attrName) -> BrowserType.CHROME
                else -> BrowserType.entries.find { browser.contains(it.attrName) } ?: BrowserType.ETC
            }
        } else {
            BrowserType.ETC
        }
    }

    fun osLogging(os: String?): OsType {
        if (os == null) return OsType.ETC

        // os
        val osRawData = os.lowercase(Locale.getDefault()).replace("\"".toRegex(), "")
        return OsType.entries.find { it.attrName == osRawData } ?: OsType.ETC
    }
}