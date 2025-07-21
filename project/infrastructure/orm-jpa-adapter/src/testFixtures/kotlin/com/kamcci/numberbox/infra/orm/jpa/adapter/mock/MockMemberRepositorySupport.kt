package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRepositorySupport
import java.util.UUID

class MockMemberRepositorySupport: MemberRepositorySupport() {
    var executeCnt = 0

    override fun updateSuccessUser(userUniqId: UUID, failCount: Int, humanStatus: Int): Long {
        executeCnt++
        return 1
    }


}