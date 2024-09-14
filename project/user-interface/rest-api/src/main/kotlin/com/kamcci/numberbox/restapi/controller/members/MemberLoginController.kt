package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.dto.AuthResponse.*
import com.kamcci.modules.auth.control.exception.BadAuthRequestException
import com.kamcci.modules.auth.control.exception.DisabledUserException
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException
import com.kamcci.modules.auth.control.exception.UserNotFoundException
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUsecase
import com.kamcci.numberbox.restapi.util.response.ResponseErrMsg
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 */
@RestController
class MemberLoginController(
    private val memberLoginFailureUsecase: MemberLoginFailureUsecase
) {

    @PostMapping("/login/fail")
    fun loginFailCallback(request: HttpServletRequest): ResponseEntity<ResponseErrMsg> {
        val exception = request.getAttribute("auth.error.exception") as Exception
        val userEmail = request.getAttribute("username") as String

        return when (exception) {
            // 클라이언트의 로그인 요청 형식이 잘못됨
            is BadAuthRequestException -> {
                ResponseUtil.error(exception, BAD_AUTH_REQUEST.statusCode, request)
            }

            // 계정 존재하지 않음
            is UserNotFoundException -> {
                ResponseUtil.error(exception, USER_NOT_FOUND.statusCode, request)
            }

            // 비밀번호 불일치
            is PasswordMissMatchException -> {
                // 과도한 비밀번호 불일치 요청시 계정 비활성화
                val isDisabled: Boolean = memberLoginFailureUsecase.disableUserIfFailCountOver(userEmail)
                if (isDisabled) ResponseUtil.error(exception, DISABLE_USER.statusCode, request)
                else ResponseUtil.error(exception, ABLE_USER.statusCode, request)
            }

            // 비활성화된 계정
            is DisabledUserException -> {
                // 계정 비활성화 유효시간이 `지난 경우 다시 활성화
                val isAfterDisableTime: Boolean = memberLoginFailureUsecase.ableUserIfDisableTimeOver(userEmail)
                if (isAfterDisableTime) ResponseUtil.error(exception, ABLE_USER.statusCode, request)
                else ResponseUtil.error(exception, PASSWORD_MISS_MATCH.statusCode, request)
            }

            else -> {
                ResponseUtil.error(exception, AUTH_SERVER_ERROR.statusCode, request)
            }
        }
    }
}