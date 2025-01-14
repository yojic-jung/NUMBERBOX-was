package com.kamcci.modules.logging.engine.util

import com.kamcci.modules.logging.control.constant.BrowserType
import com.kamcci.modules.logging.control.constant.OsType
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.browserLogging
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.osLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BrowserOsUtilTest {

    @Test
    fun `브라우저 로깅 - 실패(브라우저 정보 null)`() {
        // given
        val browser = null
        val osType = OsType.WINDOW

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.ETC)
    }


    @Test
    fun `브라우저 로깅 - 성공(os 윈도우, browser Safari)`() {
        // given
        val browser = "safari"
        val osType = OsType.WINDOW

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.SAFARI)
    }

    @Test
    fun `브라우저 로깅 - 성공(os 윈도우, browser Chrome)`() {
        // given
        val browser = "chrome"
        val osType = OsType.WINDOW

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.CHROME)
    }

    @Test
    fun `브라우저 로깅 - 성공(os 윈도우, browser 정보 없음)`() {
        // given
        val browser = ""
        val osType = OsType.WINDOW

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.ETC)
    }

    @Test
    fun `브라우저 로깅 - 성공(os Mac, browser Safari)`() {
        // given
        val browser = "safari"
        val osType = OsType.MAC

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.SAFARI)
    }

    @Test
    fun `브라우저 로깅 - 성공(os Mac, browser Chrome)`() {
        // given
        val browser = "chrome safari" // mac의 경우 chrome에 safari도 붙음
        val osType = OsType.MAC

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.CHROME)
    }


    @Test
    fun `브라우저 로깅 - 성공(os Mac, browser FIREFOX)`() {
        // given
        val browser = "FIREFOX"
        val osType = OsType.MAC

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.FIREFOX)
    }

    @Test
    fun `브라우저 로깅 - 성공(os MAC, browser 정보 없음)`() {
        // given
        val browser = ""
        val osType = OsType.MAC

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.ETC)
    }

    @Test
    fun `브라우저 로깅 - 성공(os 정보 없음)`() {
        // given
        val browser = ""
        val osType = OsType.ETC

        // when
        val browserType = browserLogging(browser, osType)

        // then
        assertThat(browserType).isEqualTo(BrowserType.ETC)
    }

    @Test
    fun `OS 로깅 - 성공(window)`() {
        // given
        val os = "window"

        // when
        val osType = osLogging(os)

        // then
        assertThat(osType).isEqualTo(OsType.WINDOW)
    }

    @Test
    fun `OS 로깅 - 성공(MAC)`() {
        // given
        val os = "MAC"

        // when
        val osType = osLogging(os)

        // then
        assertThat(osType).isEqualTo(OsType.MAC)
    }

    @Test
    fun `OS 로깅 - 성공(null)`() {
        // given
        val os = null

        // when
        val osType = osLogging(os)

        // then
        assertThat(osType).isEqualTo(OsType.ETC)
    }

    @Test
    fun `OS 로깅 - 성공(정보 없음)`() {
        // given
        val os = ""

        // when
        val osType = osLogging(os)

        // then
        assertThat(osType).isEqualTo(OsType.ETC)
    }

}
