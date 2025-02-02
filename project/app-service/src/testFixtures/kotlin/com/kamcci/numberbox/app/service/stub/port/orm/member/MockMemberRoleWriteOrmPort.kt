package com.kamcci.numberbox.app.service.stub.port.orm.member

import com.kamcci.numberbox.app.port.orm.member.MemberRoleWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMemberRoleWriteOrmPort : MemberRoleWriteOrmPort {
    override fun saveUserRole(memberId: UUID): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateEnabledById(id: UUID, enabled: Boolean): Boolean {
        return id != FAIL_MEMBER_ID
    }
}