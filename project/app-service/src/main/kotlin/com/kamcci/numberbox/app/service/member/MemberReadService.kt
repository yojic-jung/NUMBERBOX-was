package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import java.util.*

@UseCase
class MemberReadService(
    private val memberReadOrmPort: MemberReadOrmPort
) : MemberReadCase {
    override fun existEmail(email: String): Boolean =
        memberReadOrmPort.existEmail(email)

    override fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID> =
        memberReadOrmPort.readByIsTmpPassword(isTrue, limit)
}