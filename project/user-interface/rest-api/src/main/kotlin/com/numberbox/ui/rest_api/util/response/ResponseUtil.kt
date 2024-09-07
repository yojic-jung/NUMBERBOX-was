package com.numberbox.ui.rest_api.util.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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
}

