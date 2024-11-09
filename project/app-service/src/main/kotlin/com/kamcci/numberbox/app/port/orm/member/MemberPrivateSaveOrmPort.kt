package com.kamcci.numberbox.app.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import java.util.*

interface MemberPrivateSaveOrmPort {
    fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID
}