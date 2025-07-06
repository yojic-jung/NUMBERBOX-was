package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.file.FileSampleData
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class HwpConvertEventControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/hwp/convert/event"
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

   
}