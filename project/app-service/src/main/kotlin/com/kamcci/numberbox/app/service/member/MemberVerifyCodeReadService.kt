package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadCase
import java.util.*

@UseCase
class MemberVerifyCodeReadService(
    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort,
) : MemberVerifyCodeReadCase {
    companion object {
        // 인증 코드 유효 시간
        const val EMAIL_CODE_EXPIRE_TIME = 180L

        const val NOT_EXIST_CODE = "인증 코드가 존재하지 않거나 만료된 인증코드입니다."
        const val NOT_MATCHED_CODE = "인증 코드가 일치하지 않습니다."
    }

    override fun validate(codeDto: MemberVerifyCodeDto) {
        // 1. 인증 코드 존재 여부 조회
        val verifyCodeVo = memberVerifyCodeReadOrmPort.readByEmail(codeDto.email)
            ?: throw BusinessInValidException(NOT_EXIST_CODE)

        // 2. 인증 코드 일치 여부 체크
        if (codeDto.verifyCode != UUID.fromString(verifyCodeVo.verifyCode)) {
            throw BusinessInValidException(NOT_MATCHED_CODE)
        }
    }
}