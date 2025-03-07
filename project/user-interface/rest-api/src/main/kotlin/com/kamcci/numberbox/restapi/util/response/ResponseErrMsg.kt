package com.kamcci.numberbox.restapi.util.response

import java.time.LocalDateTime

/**
 * 에러 응답 템플릿
 */
data class ResponseErrMsg(
    val status: Int,
    val message: String,
    val path: String?,
    val errCode: String?,
) {
    fun getTimestamp() = LocalDateTime.now()
}
