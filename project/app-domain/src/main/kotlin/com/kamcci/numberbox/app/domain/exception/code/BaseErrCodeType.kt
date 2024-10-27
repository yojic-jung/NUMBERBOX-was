package com.kamcci.numberbox.app.domain.exception.code

/**
 * 에러 코드 형식
 */
interface BaseErrCodeType {
    val code: Int
    val message: String
}