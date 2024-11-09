package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberPrivateEntity
import java.util.*

object MemberPrivateFactory {
    fun getSaveEntity(uuid: UUID, privateSignUpDto: MemberPrivateSignUpDto) =
        MemberPrivateEntity()
            .apply {
                memberId = uuid
                userName = privateSignUpDto.userName
                phoneNumber = privateSignUpDto.phoneNumber
                birth = privateSignUpDto.birth
            }

}