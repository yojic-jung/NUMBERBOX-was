package com.kamcci.numberbox.app.port.orm.member

import java.util.*

/**
 * 회원 권한 조회
 */
interface MemberRoleReadOrmPort {
    /**
     * user 권한 조회
     */
    fun readRoleByMemberId(memberId: UUID): List<String>
}