package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.file.FileSampleData
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class HwpConvertControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/hwp/convert"
        const val JSON_TO_HWP = "$PREFIX/json-to-hwp"
        const val HWP_TO_HTML = "$PREFIX/hwp-to-html"
    }

    @Test
    fun `json to hwp 변환 요청 - 성공`() {
        // given
        val reqBody = mapOf(
            "jsonMsg" to "asdfjlalf",
        )

        // when
        val resultAction = postRequest(JSON_TO_HWP, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `hwp to html `() {
        // given
        val multipartFile = FileSampleData.getMultipartFile("hwpFile", "test.hwp")

        // when
        val resultAction = postMultipartForm(HWP_TO_HTML, multipartFile)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `hwp 변환 컨텐츠 수정 - 성공`() {
        // given
        val reqBody = mapOf(
            "id" to "1",
            "contents" to "asdfjlalf",
        )

        // when
        val resultAction = putRequest(HWP_TO_HTML, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `hwp 변환 컨텐츠 수정 - 실패`() {
        // given
        val reqBody = mapOf(
            "id" to FAIL_ID,
            "contents" to "asdfjlalf",
        )

        // when
        val resultAction = putRequest(HWP_TO_HTML, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }

    @Test
    fun `hwp 변환 컨텐츠 삭제 - 성공`() {
        // given
        val contentsId = 1L

        // when
        val resultAction = delRequest("$HWP_TO_HTML/$contentsId", null)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `hwp 변환 컨텐츠 삭제 - 실패`() {
        // given
        val contentsId = FAIL_ID

        // when
        val resultAction = delRequest("$HWP_TO_HTML/$contentsId", null)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }
}