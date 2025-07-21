package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.modules.logging.control.service.IPAddressService
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth.AuthUserInfoRepository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.inc

class MockAuthUserInfoRepository: AuthUserInfoRepository(MockIPAddressService()) {
    var executeCnt = 0
    val dataStore = ConcurrentHashMap<String, Any>()

    override fun canReCreateRefreshToken(userId: UUID): Boolean {
        executeCnt++
        return true
    }

    override fun loadUserIdByRefreshToken(token: String): UUID? {
        executeCnt++
        return UUID.randomUUID()
    }

    override fun findByEmail(username: String): MemberEntity? {
        return dataStore.get(username) as MemberEntity?
    }
}

class MockIPAddressService: IPAddressService {
    override fun getIPAddress(): String {
        return "nothing"
    }

    override fun getPublicIPAddress(): String {
        return "nothing"
    }
}