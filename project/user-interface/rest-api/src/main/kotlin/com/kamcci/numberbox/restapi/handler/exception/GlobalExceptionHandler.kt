package com.kamcci.numberbox.restapi.handler.exception

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * 컨트롤러 예외 처리 목적 aop 프록시
 */
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    // MethodArgumentNotValid
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.warn(ex)
        return ResponseUtil.error(ex, status, request)
    }

    @ExceptionHandler(value = [BusinessValidException::class])
    fun handleBusinessInValidException(
        ex: Exception,
        body: Any?,
        request: WebRequest
    ): ResponseEntity<Any> {
        println(ex.stackTraceToString())
//        logger.warn(ex)
        return ResponseUtil.error(ex, HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(
        ex: Exception,
        body: Any?,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error(ex)
        return ResponseUtil.error(ex, HttpStatus.INTERNAL_SERVER_ERROR, request)
    }


    /**
     * 모든 예외타입에 대하여 응답 형식 지정
     */
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.warn(ex)
        return ResponseUtil.error(ex, statusCode, request)
    }
}