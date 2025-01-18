package com.kamcci.numberbox.restapi.util.response

import com.kamcci.numberbox.app.domain.exception.BusinessErrCodeException
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
        exception: Throwable,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode.value(), request.contextPath)
    }

    // HttpServletRequest를 사용하는 경우
    fun error(
        exception: Throwable,
        statusCode: HttpStatus,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return error(exception, statusCode.value(), request.requestURI)
    }


    // 에러메시지 응답 반환
    private fun error(
        exception: Throwable,
        statusCode: Int,
        requestUri: String,
    ): ResponseEntity<Any> {
        val errCode = if (exception is BusinessErrCodeException) exception.errType.errCode
        else null

        val responseErrMsg =
            ResponseErrMsg(
                status = statusCode,
                message = exception.message.toString(),
                path = requestUri,
                errCode = errCode
            )
        return ResponseEntity(responseErrMsg, HttpStatusCode.valueOf(statusCode))
    }
}
