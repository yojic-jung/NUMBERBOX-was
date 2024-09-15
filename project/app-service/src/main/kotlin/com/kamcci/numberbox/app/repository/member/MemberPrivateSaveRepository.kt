package com.kamcci.numberbox.app.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import java.util.*

interface MemberPrivateSaveRepository {
    fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID
}