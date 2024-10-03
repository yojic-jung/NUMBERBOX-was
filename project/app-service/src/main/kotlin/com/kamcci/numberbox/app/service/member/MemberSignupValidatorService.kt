package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo.SignUpResultMSg.*
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberSignupValidator
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@UseCase
class MemberSignupValidatorService(
    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort,
    private val memberReadOrmPort: MemberReadOrmPort,
) : MemberSignupValidator {
    companion object {
        // 이메일 인증 코드 유효 시간
        private const val EMAIL_CODE_EXPIRE_TIME = 180
    }

    override fun validate(signUpDto: MemberSignUpDto): MemberSignUpResultVo? {
        val emailVerifyCodeVo = memberVerifyCodeReadOrmPort.findByEmail(signUpDto.email)
            ?: throw BusinessInValidException("이메일 검증 코드 미존재시 회원가입이 불가합니다.")
        val duration = Duration.between(emailVerifyCodeVo.sysCreateTime, LocalDateTime.now())

        // 1. 이메일 인증 코드 만료여부 체크
        if (duration.seconds > EMAIL_CODE_EXPIRE_TIME) return MemberSignUpResultVo(false, EXPIRED_MSG)

        // 2. 이메일 코드 일치 여부 체크
        if (signUpDto.emailVerifyCode != UUID.fromString(emailVerifyCodeVo.verifyCode)) {
            return MemberSignUpResultVo(false, NOT_MATCH_CODE_MSG)
        }

        // 3. 이메일 중복 여부 체크
        val isEmailExists = memberReadOrmPort.existsByEmail(signUpDto.email)
        if (isEmailExists) return MemberSignUpResultVo(false, EXIST_EMAIL_MSG)

        return null
    }
}