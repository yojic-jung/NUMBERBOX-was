package com.kamcci.numberbox.restapi.util.response

import com.kamcci.numberbox.app.domain.exception.BusinessErrCodeException
import com.kamcci.numberbox.restapi.exception.code.RestApiErrCodeType
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

class ResponseUtilTest {
    @Test
    fun `Default 정상 응답 - 성공`() {
        // when
        val res = ResponseUtil.ok()

        // then
        assertThat(res.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Body 값 있는 정상 응답 - 성공`() {
        // given
        val body = "body data"

        // when
        val res = ResponseUtil.ok(body)

        // then
        assertThat(res.body?.data).isEqualTo(body)
    }

    @Test
    fun `WebRequest를 사용하는 경우 에러 응답 - 성공`() {
        // given
        val sttsCode = HttpStatus.BAD_REQUEST
        val mockServletRequest = MockHttpServletRequest()
        val mockWebRequest: WebRequest = ServletWebRequest(mockServletRequest)
        mockServletRequest.contextPath = "URI"
        val now = LocalDateTime.now()

        // when
        val errRes = ResponseUtil.error(RuntimeException(""), sttsCode, mockWebRequest)

        // then
        assertThat(errRes.statusCode).isEqualTo(sttsCode)
        val errMsg = errRes.body as ResponseErrMsg
        assertThat(errMsg.getTimestamp()).isAfterOrEqualTo(now)
    }

    @Test
    fun `HttpServletRequest를 사용하는 경우 에러 응답 - 성공`() {
        // given
        val sttsCode = HttpStatus.BAD_REQUEST
        val mockServletRequest = MockHttpServletRequest()
        val mockWebRequest: WebRequest = ServletWebRequest(mockServletRequest)
        mockServletRequest.contextPath = "URI"

        // when
        val errRes = ResponseUtil.error(RuntimeException(""), sttsCode, mockWebRequest)

        // then
        assertThat(errRes.statusCode).isEqualTo(sttsCode)
    }

    @Test
    fun `에러 코드 포함한 응답 - 성공`() {
        // given
        val errType = RestApiErrCodeType.DISABLE_USER
        val sttsCode = HttpStatus.BAD_REQUEST
        val mockServletRequest = MockHttpServletRequest()
        val mockWebRequest: WebRequest = ServletWebRequest(mockServletRequest)
        mockServletRequest.contextPath = "URI"

        // when
        val errRes = ResponseUtil.error(BusinessErrCodeException(errType), sttsCode, mockWebRequest)

        // then
        assertThat(errRes.statusCode).isEqualTo(sttsCode)
        assertThat((errRes.body as ResponseErrMsg).errCode).isEqualTo(errType.errCode)
    }
}