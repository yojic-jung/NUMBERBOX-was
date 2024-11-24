package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadUseCase
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@UseCase
class MemberVerifyCodeReadService(
    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort,
) : MemberVerifyCodeReadUseCase {
    companion object {
        // 인증 코드 유효 시간
        private const val EMAIL_CODE_EXPIRE_TIME = 180
    }

    override fun validate(codeDto: MemberVerifyCodeDto) {
        // 1. 인증 코드 존재 여부 조회
        val verifyCodeVo = memberVerifyCodeReadOrmPort.readByEmailAndCodeType(codeDto.email, codeDto.verifyCodeType)
            ?: throw BusinessValidException("인증 코드가 존재하지 않습니다.")

        // 2. 인증 코드 만료여부 체크
        val duration = Duration.between(verifyCodeVo.sysCreateTime, LocalDateTime.now())
        if (duration.seconds > EMAIL_CODE_EXPIRE_TIME) throw BusinessValidException("만료된 인증 코드입니다.")

        // 3. 인증 코드 일치 여부 체크
        if (codeDto.verifyCode != UUID.fromString(verifyCodeVo.verifyCode)) {
            throw BusinessValidException("인증 코드가 일치하지 않습니다.")
        }
    }
}