package com.kamcci.numberbox.app.usecase.member

import java.util.*

/**
 * 사용자 정보 변경
 */
interface MemberDropCase {
    /**
     * 회원 탈퇴
     */
    fun drop(memberId: UUID)
}