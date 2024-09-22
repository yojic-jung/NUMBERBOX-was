package com.kamcci.numberbox.app.repository.member

import java.util.*

interface MemberRoleSaveRepository {
    /**
     * user 권한 사용자 권한 생성
     */
    fun saveUserRole(memberId: UUID): Long
}