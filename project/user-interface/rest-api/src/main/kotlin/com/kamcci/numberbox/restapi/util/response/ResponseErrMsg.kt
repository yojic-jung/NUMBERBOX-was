package com.kamcci.numberbox.restapi.util.response

import java.time.LocalDateTime

/**
 * 응답 템플릿
 */
data class ResponseErrMsg(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int = SUCCESS_STATUS,
    val showMsg: Boolean = false,
    val message: String = SUCCESS_MESSAGE,
    val path: String?,
) {
    companion object {
        private const val SUCCESS_MESSAGE = "성공하였습니다."
        private const val SUCCESS_STATUS = 200
    }
}
