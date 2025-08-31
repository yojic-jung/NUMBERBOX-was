package com.kamcci.numberbox.app.port.orm.member

import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo

/**
 * Def. 회원 검증 인증코드 조회
 */
interface MemberVerifyCodeReadOrmPort {
    fun readByEmail(email: String): MemberVerifyCodeVo?
}