package com.kamcci.numberbox.app.service.mock.usecase.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_USER_NAME
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import java.time.LocalDateTime
import java.util.*

class MockMemberReadCase : MemberReadCase {
    companion object {
        const val DISABLE_MEMBER = "disable@test.com"
        const val BEFORE_20m_AGO_FAIL_EMAIL = "before20mFail@test.com"
        const val NONE_FAIL_DISABLE_MEMBER = "noneFail@test.com"
    }

    private val BEFORE_20m_AGO_FAIL_ID = UUID.randomUUID()
    private val NONE_FAIL_DISABLE_MEMBER_ID = UUID.randomUUID()

    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var moreBatchSize: Boolean = false // 배치 사이즈 이상 조회 여부
    var executeCnt = 0 // 실행 횟수
    var failCnt: Int? = null // 실패 횟수 반환 값

    override fun readIdByEmail(email: String): UUID? {
        return when {
            email == FAIL_EMAIL -> null
            email == DISABLE_MEMBER -> FAIL_MEMBER_ID
            email == BEFORE_20m_AGO_FAIL_EMAIL -> BEFORE_20m_AGO_FAIL_ID
            email == NONE_FAIL_DISABLE_MEMBER -> NONE_FAIL_DISABLE_MEMBER_ID
            else -> UUID.randomUUID()
        }
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
        return if (id == FAIL_MEMBER_ID) null else failCnt ?: 1
    }

    override fun readLastFailTimeById(id: UUID): LocalDateTime? {
        val now = LocalDateTime.now()
        return when {
            (id == BEFORE_20m_AGO_FAIL_ID) -> now.minusMinutes(20)
            (id == NONE_FAIL_DISABLE_MEMBER_ID) -> null
            else -> now
        }
    }

    override fun existsByEmail(email: String): Boolean {
        return email == EXIST_EMAIL
    }

    override fun readByIsTmpPassword(isTmpPwCond: Boolean, limit: Long): List<UUID> {
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