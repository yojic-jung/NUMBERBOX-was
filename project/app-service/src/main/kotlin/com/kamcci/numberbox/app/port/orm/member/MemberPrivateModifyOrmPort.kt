package com.kamcci.numberbox.app.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto

interface MemberPrivateModifyOrmPort {
    /**
     * 휴대폰 번호 변경
     */
    fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean

}