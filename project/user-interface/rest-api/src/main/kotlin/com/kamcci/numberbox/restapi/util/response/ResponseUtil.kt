package com.kamcci.numberbox.restapi.util.response

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.WebRequest


/**
 * 응답 형식 반환 util
 */
object ResponseUtil {
    /**
     * 성공 응답
     */
    fun <T> ok(data: T): ResponseEntity<ResponseData<T>> {
        val responseData = ResponseData(data = data)
        return ResponseEntity(responseData, HttpStatus.valueOf(responseData.status))
    }

    // WebRequest를 사용하는 경우
    fun error(exception: Exception, statusCode: HttpStatusCode, request: WebRequest): ResponseEntity<Any> {
        return error(exception, statusCode, false, request)
    }

    fun error(
        exception: Exception,
        statusCode: HttpStatusCode,
        showMsg: Boolean,
        request: WebRequest
    ): ResponseEntity<Any> {
        val uri = if (request is HttpServletRequest) {
            request.requestURI
        } else {
            request.contextPath
        }
        return error(exception, statusCode.value(), showMsg, uri)
    }

    // HttpServletRequest를 사용하는 경우
    fun error(
        exception: Exception,
        statusCode: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode, false, request.requestURI)
    }

    fun error(
        exception: Exception,
        statusCode: Int,
        showMsg: Boolean,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode, showMsg, request.requestURI)
    }

    // 에러메시지 응답 반환
    private fun error(
        exception: Exception,
        statusCode: Int,
        showMsg: Boolean,
        requestUri: String
    ): ResponseEntity<Any> {
        val responseErrMsg =
            ResponseErrMsg(
                status = statusCode,
                showMsg = showMsg,
                message = exception.message.toString(),
                path = requestUri
            )
        return ResponseEntity(responseErrMsg, HttpStatusCode.valueOf(statusCode))
    }
}
