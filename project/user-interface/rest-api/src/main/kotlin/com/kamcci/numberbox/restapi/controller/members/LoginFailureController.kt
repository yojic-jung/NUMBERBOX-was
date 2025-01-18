package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.exception.BadAuthRequestException
import com.kamcci.modules.auth.control.exception.DisabledUserException
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException
import com.kamcci.modules.auth.control.exception.UserNotFoundException
import com.kamcci.numberbox.app.domain.exception.BusinessErrCodeException
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase
import com.kamcci.numberbox.restapi.exception.code.RestApiErrCodeType
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 실패 콜백
 */
@RestController
class LoginFailureController(
    private val memberLoginFailureUsecase: MemberLoginFailureUseCase
) {

    @PostMapping("/login/fail")
    fun loginFailCallback(request: HttpServletRequest): ResponseEntity<Any> {
        val exception = request.getAttribute("auth.error.exception") as Exception
        val userEmail = request.getAttribute("username") as String
        return when (exception) {
            // 클라이언트의 로그인 요청 형식이 잘못됨
            is BadAuthRequestException -> {
                ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.BAD_AUTH_REQUEST),
                    HttpStatus.BAD_REQUEST,
                    request
                )
            }

            // 계정 존재하지 않음
            is UserNotFoundException -> {
                ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.USER_NOT_FOUND),
                    HttpStatus.UNAUTHORIZED,
                    request
                )
            }

            // 비밀번호 불일치
            is PasswordMissMatchException -> {
                // 과도한 비밀번호 불일치 요청시 계정 비활성화
                val isDisabled: Boolean = memberLoginFailureUsecase.disableUserIfFailCountOver(userEmail)
                if (isDisabled) ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.DISABLE_USER),
                    HttpStatus.FORBIDDEN, request
                )
                else ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.PASSWORD_MISS_MATCH),
                    HttpStatus.FORBIDDEN, request
                )
            }

            // 비활성화된 계정
            is DisabledUserException -> {
                // 계정 비활성화 유효시간이 `지난 경우 다시 활성화
                val isAfterDisableTime: Boolean = memberLoginFailureUsecase.ableUserIfDisableTimeOver(userEmail)
                if (isAfterDisableTime) ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.DISABLE_TO_ABLE),
                    HttpStatus.FORBIDDEN,
                    request
                )
                else ResponseUtil.error(
                    BusinessErrCodeException(RestApiErrCodeType.DISABLE_USER),
                    HttpStatus.FORBIDDEN,
                    request
                )
            }

            else -> {
                ResponseUtil.error(exception, HttpStatus.INTERNAL_SERVER_ERROR, request)
            }
        }
    }
}