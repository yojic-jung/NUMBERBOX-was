package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberReadUseCase

@UseCase
class MemberReadService(
    private val memberReadOrmPort: MemberReadOrmPort
) : MemberReadUseCase {
    override fun existEmail(email: String): Boolean =
        memberReadOrmPort.existEmail(email)
}