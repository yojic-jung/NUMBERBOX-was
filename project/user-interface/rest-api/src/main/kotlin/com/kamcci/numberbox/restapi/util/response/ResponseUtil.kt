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

    fun error(exception: Exception, statusCode: HttpStatusCode, request: WebRequest): ResponseEntity<Any> {
        val requestUri = request.contextPath
        val responseErrMsg =
            ResponseErrMsg(status = statusCode.value(), message = exception.message.toString(), path = requestUri)
        return ResponseEntity(responseErrMsg, statusCode)
    }

    fun error(exception: Exception, statusCode: Int, request: HttpServletRequest): ResponseEntity<ResponseErrMsg> {
        val requestUri = request.contextPath
        val responseErrMsg =
            ResponseErrMsg(status = statusCode, message = exception.message.toString(), path = requestUri)
        return ResponseEntity(responseErrMsg, HttpStatusCode.valueOf(statusCode))
    }
}
