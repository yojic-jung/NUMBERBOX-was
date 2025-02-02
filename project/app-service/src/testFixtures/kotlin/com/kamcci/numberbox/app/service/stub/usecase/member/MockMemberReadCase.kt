package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_USER_NAME
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import java.time.LocalDateTime
import java.util.*

class MockMemberReadCase : MemberReadCase {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var moreBatchSize: Boolean = false // 배치 사이즈 이상 조회 여부
    var executeCnt = 0 // 실행 횟수

    override fun readIdByEmail(email: String): UUID? {
        return if (email == FAIL_EMAIL) null else UUID.randomUUID()
    }

    override fun readEmailByUsernameAndPhone(userName: String, phoneNumber: String): String? {
        return if (userName == FAIL_USER_NAME) null else "success"
    }

    override fun existEmail(email: String): Boolean {
        return email != FAIL_EMAIL
    }

    override fun readPasswordByMemberId(memberId: UUID): String? {
        return if (memberId == FAIL_MEMBER_ID) null else "success"
    }

    override fun readFailCountById(id: UUID): Int? {
        return if (id == FAIL_MEMBER_ID) null else 1
    }

    override fun readLastFailTimeById(id: UUID): LocalDateTime? {
        return LocalDateTime.now()
    }

    override fun existsByEmail(email: String): Boolean {
        return email != FAIL_EMAIL
    }

    override fun readByIsTmpPassword(isTrue: Boolean, limit: Long): List<UUID> {
        executeCnt++
        val memberIds: MutableList<UUID> = mutableListOf()
        if (moreBatchSize && executeCnt == 1) {
            for (i in 1..600) memberIds.add(UUID.randomUUID())
        } else {
            for (i in 1..100) memberIds.add(UUID.randomUUID())
        }
        return memberIds
    }

    override fun readUserIdByHumanStatus(humanStatus: Int): List<UUID> {
        val memberIds: MutableList<UUID> = mutableListOf()
        for (i in 1..100) memberIds.add(UUID.randomUUID())
        return memberIds
    }
}