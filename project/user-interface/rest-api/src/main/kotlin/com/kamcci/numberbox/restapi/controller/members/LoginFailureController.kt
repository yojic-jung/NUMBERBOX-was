package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.dto.AuthResponse.*
import com.kamcci.modules.auth.control.exception.BadAuthRequestException
import com.kamcci.modules.auth.control.exception.DisabledUserException
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException
import com.kamcci.modules.auth.control.exception.UserNotFoundException
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUsecase
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 실패 콜백
 */
@RestController
class LoginFailureController(
    private val memberLoginFailureUsecase: MemberLoginFailureUsecase
) {

    @PostMapping("/login/fail")
    fun loginFailCallback(request: HttpServletRequest): ResponseEntity<Any> {
        val exception = request.getAttribute("auth.error.exception") as Exception
        val userEmail = request.getAttribute("username") as String
        val isShowErrMsg = false

        return when (exception) {
            // 클라이언트의 로그인 요청 형식이 잘못됨
            is BadAuthRequestException -> {
                ResponseUtil.error(exception, BAD_AUTH_REQUEST.statusCode, isShowErrMsg, request)
            }

            // 계정 존재하지 않음
            is UserNotFoundException -> {
                ResponseUtil.error(exception, USER_NOT_FOUND.statusCode, isShowErrMsg, request)
            }

            // 비밀번호 불일치
            is PasswordMissMatchException -> {
                // 과도한 비밀번호 불일치 요청시 계정 비활성화
                val isDisabled: Boolean = memberLoginFailureUsecase.disableUserIfFailCountOver(userEmail)
                if (isDisabled) ResponseUtil.error(
                    DisabledUserException(),
                    DISABLE_USER.statusCode,
                    isShowErrMsg,
                    request
                )
                else ResponseUtil.error(exception, PASSWORD_MISS_MATCH.statusCode, isShowErrMsg, request)
            }

            // 비활성화된 계정
            is DisabledUserException -> {
                // 계정 비활성화 유효시간이 `지난 경우 다시 활성화
                val isAfterDisableTime: Boolean = memberLoginFailureUsecase.ableUserIfDisableTimeOver(userEmail)
                if (isAfterDisableTime) ResponseUtil.error(
                    DisabledUserException("계정 잠금이 해제 되었습니다.\n다시 로그인 시도해주세요."),
                    ABLE_USER.statusCode,
                    isShowErrMsg,
                    request
                )
                else ResponseUtil.error(exception, DISABLE_USER.statusCode, isShowErrMsg, request)
            }

            else -> {
                ResponseUtil.error(exception, AUTH_SERVER_ERROR.statusCode, isShowErrMsg, request)
            }
        }
    }
}