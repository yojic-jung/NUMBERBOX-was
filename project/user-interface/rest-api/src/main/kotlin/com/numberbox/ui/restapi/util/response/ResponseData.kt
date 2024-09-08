package com.numberbox.ui.restapi.util.response

import java.sql.Timestamp

/**
 * 응답 템플릿
 */
data class ResponseData<T>(
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val status: Int = SUCCESS_STATUS,
    val message: String = SUCCESS_MESSAGE,
    val data: T,
) {
    companion object {
        private const val SUCCESS_MESSAGE = "성공하였습니다."
        private const val SUCCESS_STATUS = 200
    }
}
