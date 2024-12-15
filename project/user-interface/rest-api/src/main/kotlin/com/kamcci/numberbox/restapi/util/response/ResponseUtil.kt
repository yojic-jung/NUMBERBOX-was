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
    fun ok(): ResponseEntity<ResponseData<String>> {
        val responseData = ResponseData(data = "ok")
        return ResponseEntity(responseData, HttpStatus.valueOf(responseData.status))
    }

    fun <T> ok(data: T): ResponseEntity<ResponseData<T>> {
        val responseData = ResponseData(data = data)
        return ResponseEntity(responseData, HttpStatus.valueOf(responseData.status))
    }

    // WebRequest를 사용하는 경우
    fun error(
        exception: Exception,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val uri = if (request is HttpServletRequest) {
            request.requestURI
        } else {
            request.contextPath
        }
        return error(exception, statusCode.value(), uri)
    }

    fun error(
        exception: Throwable,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val uri = if (request is HttpServletRequest) {
            request.requestURI
        } else {
            request.contextPath
        }
        return error(exception, statusCode.value(), uri)
    }

    // HttpServletRequest를 사용하는 경우
    fun error(
        exception: Exception,
        statusCode: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode, request.requestURI)
    }

    fun error(
        exception: Exception,
        statusCode: HttpStatus,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode.value(), request.requestURI)
    }


    // 에러메시지 응답 반환
    private fun error(
        exception: Exception,
        statusCode: Int,
        requestUri: String
    ): ResponseEntity<Any> {
        val responseErrMsg =
            ResponseErrMsg(
                status = statusCode,
                message = exception.message.toString(),
                path = requestUri
            )
        return ResponseEntity(responseErrMsg, HttpStatusCode.valueOf(statusCode))
    }

    private fun error(
        exception: Throwable,
        statusCode: Int,
        requestUri: String
    ): ResponseEntity<Any> {
        val responseErrMsg =
            ResponseErrMsg(
                status = statusCode,
                message = exception.message.toString(),
                path = requestUri
            )
        return ResponseEntity(responseErrMsg, HttpStatusCode.valueOf(statusCode))
    }
}
