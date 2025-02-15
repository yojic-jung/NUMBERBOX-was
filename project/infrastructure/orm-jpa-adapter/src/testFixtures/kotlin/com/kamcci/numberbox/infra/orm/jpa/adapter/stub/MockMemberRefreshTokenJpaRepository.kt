package com.kamcci.numberbox.infra.orm.jpa.adapter.stub

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenJpaRepository

class MockMemberRefreshTokenJpaRepository : MemberRefreshTokenJpaRepository() {

    override fun save(memberRefreshTokenEntity: MemberRefreshTokenEntity): Long {
        return 1
    }

    override fun deleteByToken(token: String) {

    }
}