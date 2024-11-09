package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateModifyOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberPrivateModifyUseCase

@UseCase
class MemberPrivateModifyService(
    private val memberPrivateModifyOrmPort: MemberPrivateModifyOrmPort,
) : MemberPrivateModifyUseCase {
    @TXExecute
    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        return memberPrivateModifyOrmPort.updatePhoneNumber(phoneUpdtDto)
    }
}
