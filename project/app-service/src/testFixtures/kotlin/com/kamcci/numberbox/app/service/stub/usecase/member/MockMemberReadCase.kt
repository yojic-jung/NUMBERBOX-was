package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import java.time.LocalDateTime
import java.util.*

class MockMemberReadCase : MemberReadCase {
    override fun readIdByEmail(email: String): UUID? {
        TODO("Not yet implemented")
    }

    override fun readEmailByUsernameAndPhone(userName: String, phoneNumber: String): String? {
        TODO("Not yet implemented")
    }

    override fun existEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun readPasswordByMemberId(memberId: UUID): String? {
        TODO("Not yet implemented")
    }

    override fun readFailCountById(id: UUID): Int? {
        TODO("Not yet implemented")
    }

    override fun readLastFailTimeById(id: UUID): LocalDateTime? {
        TODO("Not yet implemented")
    }

    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID> {
        TODO("Not yet implemented")
    }

    override fun readUserIdByHumanStatus(humanStatus: Int): List<UUID> {
        TODO("Not yet implemented")
    }
}