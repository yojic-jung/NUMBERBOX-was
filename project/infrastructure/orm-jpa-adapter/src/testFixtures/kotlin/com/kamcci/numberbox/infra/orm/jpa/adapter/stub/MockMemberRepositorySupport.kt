package com.kamcci.numberbox.infra.orm.jpa.adapter.stub

import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRepositorySupport
import java.util.*

class MockMemberRepositorySupport : MemberRepositorySupport() {
    override fun updateSuccessUser(userUniqId: UUID, failCount: Int, humanStatus: Int): Long {
        return 1
    }
}