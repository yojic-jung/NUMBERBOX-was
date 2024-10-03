package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo.VerifyResultMSg.*
import com.kamcci.numberbox.app.port.repository.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadUseCase
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@UseCase
class MemberVerifyCodeReadUseCaseService(
    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort,
) : MemberVerifyCodeReadUseCase {
    companion object {
        // 인증 코드 유효 시간
        private const val EMAIL_CODE_EXPIRE_TIME = 180
    }

    override fun validate(codeDto: MemberVerifyCodeDto): MemberVerifyCodeResultVo {
        // 1. 인증 코드 존재 여부 조회
        val verifyCodeVo = memberVerifyCodeReadOrmPort.findByEmailAndCodeType(codeDto.email, codeDto.verifyCodeType)
            ?: return MemberVerifyCodeResultVo(false, NOT_EXIST)

        // 2. 인증 코드 만료여부 체크
        val duration = Duration.between(verifyCodeVo.sysCreateTime, LocalDateTime.now())
        if (duration.seconds > EMAIL_CODE_EXPIRE_TIME) return MemberVerifyCodeResultVo(false, EXPIRED_MSG)

        // 3. 인증 코드 일치 여부 체크
        if (codeDto.verifyCode != UUID.fromString(verifyCodeVo.verifyCode)) {
            return MemberVerifyCodeResultVo(false, NOT_MATCH_CODE_MSG)
        }

        return MemberVerifyCodeResultVo(true, SUCCESS_MSG)
    }
}