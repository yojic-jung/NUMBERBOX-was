package com.kamcci.numberbox.app.service

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.repository.member.MemberModifyRepository
import com.kamcci.numberbox.app.repository.member.MemberReadRepository
import com.kamcci.numberbox.app.repository.member.MemberRoleModifyRepository
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUsecase
import java.time.LocalDateTime

@UseCase
class MemberLoginFailureService(
    private val memberReadRepository: MemberReadRepository,
    private val memberModifyRepository: MemberModifyRepository,
    private val membersRoleModifyRepository: MemberRoleModifyRepository
) : MemberLoginFailureUsecase {
    companion object {
        // 계정 비활성화 실패 카운트 기준
        private const val DISABLE_COUNT = 4

        // 계정 비활성화 잠금 시간 기준
        private const val DISABLE_LOCK_TIME = 15L
    }

    @TXExecute
    override fun disableUserIfFailCountOver(email: String): Boolean {
        val id = memberReadRepository.findIdByEmail(email)
        val failCount: Int = memberReadRepository.findFailCountById(id)

        // 비활성화 실패 카운트 기준 초과시 enabled=false 변경
        if (failCount == DISABLE_COUNT) {
            membersRoleModifyRepository.updateEnabledById(id, false)
        }
        // 실패 카운트 +1
        memberModifyRepository.updateFailCountById(id, failCount + 1)
        return failCount >= DISABLE_COUNT
    }

    @TXExecute
    override fun ableUserIfDisableTimeOver(email: String): Boolean {
        val userId = memberReadRepository.findIdByEmail(email)
        val lastFailTime: LocalDateTime = memberReadRepository.findLastFailTimeById(userId)
        val isAfterLockTime = lastFailTime.plusMinutes(DISABLE_LOCK_TIME).isBefore(LocalDateTime.now())

        // 비활성화 잠금 시간 지나면 enabled=true, failCount=0로 변경(로그인 시도 가능하도록)
        if (isAfterLockTime) {
            membersRoleModifyRepository.updateEnabledById(userId, false)
            memberModifyRepository.updateFailCountById(userId, 0)
        } else {
            // 비활성화 잠금 시간이 지나지 않으면 마지막 실패 시간만 변경(지속적으로 실패시 계정 잠금시간을 늘리기 위하여)
            memberModifyRepository.updateLastFailTimeById(userId, LocalDateTime.now())
        }
        return isAfterLockTime
    }
}