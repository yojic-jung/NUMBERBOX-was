package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.BrowserType
import com.kamcci.modules.logging.control.constant.OsType
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.browserLogging
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.osLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BrowserOsUtilTest {

    // 브라우저 로깅 테스트 케이스
    private var browserLogTCList: MutableList<Triple<String?, OsType, BrowserType>> = mutableListOf()

    private fun setUpBrowserTC() {
        browserLogTCList.add(Triple(null, OsType.WINDOW, BrowserType.ETC))
        browserLogTCList.add(Triple("safari", OsType.WINDOW, BrowserType.SAFARI))
        browserLogTCList.add(Triple("chrome", OsType.WINDOW, BrowserType.CHROME))
        browserLogTCList.add(Triple("", OsType.WINDOW, BrowserType.ETC))
        browserLogTCList.add(Triple("safari", OsType.MAC, BrowserType.SAFARI))
        browserLogTCList.add(Triple("chrome safari", OsType.MAC, BrowserType.CHROME))
        browserLogTCList.add(Triple("FIREFOX", OsType.MAC, BrowserType.FIREFOX))
        browserLogTCList.add(Triple("", OsType.MAC, BrowserType.ETC))
        browserLogTCList.add(Triple("", OsType.ETC, BrowserType.ETC))
    }

    @Test
    fun `브라우저 로깅`() {
        // given
        setUpBrowserTC()

        browserLogTCList.forEach {
            val browserStr = it.first // 브라우저 문자열 추출 정보
            val osType = it.second

            // when
            val browserType = browserLogging(browserStr, osType)

            // then
            val expectedType = it.third
            assertThat(browserType).isEqualTo(expectedType)
        }
    }

    // os 로깅 테스트 케이스
    private var osLogTCList: MutableList<Pair<String?, OsType>> = mutableListOf()

    private fun setUpOsTC() {
        osLogTCList.add(Pair("window", OsType.WINDOW))
        osLogTCList.add(Pair("MAC", OsType.MAC))
        osLogTCList.add(Pair(null, OsType.ETC))
        osLogTCList.add(Pair("", OsType.ETC))
    }

    @Test
    fun `OS 로깅`() {
        // given
        setUpOsTC()

        // when
        osLogTCList.forEach {
            val osStr = it.first // os 문자열 추출 정보

            // when
            val osType = osLogging(osStr)

            // then
            val expectedType = it.second
            assertThat(osType).isEqualTo(expectedType)
        }
    }
}
