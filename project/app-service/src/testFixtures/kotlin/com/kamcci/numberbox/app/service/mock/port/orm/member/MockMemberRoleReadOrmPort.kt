package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleReadOrmPort
import java.util.*

class MockMemberRoleReadOrmPort : MemberRoleReadOrmPort {
    override fun readRoleByMemberId(memberId: UUID): List<String> {
        return listOf("USER")
    }
}