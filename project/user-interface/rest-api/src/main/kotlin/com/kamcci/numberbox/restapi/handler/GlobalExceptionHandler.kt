package com.kamcci.numberbox.restapi.handler

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.beans.BeanInstantiationException
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

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.info(ex)
        return ResponseUtil.error(ex, status, request)
    }

    /**
     * controller dto객체 require 만족 못시키는 경우
     * IllegalArgumentException -> BeanInstantiationException으로 예외 포장되는 경우 분기 처리
     */
    @ExceptionHandler(value = [IllegalArgumentException::class, BeanInstantiationException::class])
    fun handleIllegalArgumentException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        val actualCause: Throwable = if (ex is BeanInstantiationException) ex.cause ?: ex else ex

        val status = when (actualCause) {
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST // 400
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        // 예외 정보 출력
        logger.info(ex)

        return ResponseUtil.error(actualCause, status, request)
    }

    @ExceptionHandler(value = [BusinessValidException::class])
    fun handleBusinessInValidException(
        ex: Exception,
        body: Any?,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(ex)
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