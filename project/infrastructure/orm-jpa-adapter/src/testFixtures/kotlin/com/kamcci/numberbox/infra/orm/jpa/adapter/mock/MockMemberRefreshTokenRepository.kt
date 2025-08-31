package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenRepository

class MockMemberRefreshTokenRepository : MemberRefreshTokenRepository() {

    // 실행횟수
    var executeCnt = 0

    override fun save(memberRefreshTokenEntity: MemberRefreshTokenEntity): Long {
        executeCnt++
        return 1
    }

    override fun deleteByToken(token: String): Long {
        executeCnt++
        return 1L
    }
}