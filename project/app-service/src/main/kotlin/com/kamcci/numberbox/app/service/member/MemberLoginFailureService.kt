package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.member.MemberModifyOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberRoleModifyOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUsecase
import java.time.LocalDateTime

@UseCase
class MemberLoginFailureService(
    private val memberReadOrmPort: MemberReadOrmPort,
    private val memberModifyOrmPort: MemberModifyOrmPort,
    private val membersRoleModifyRepository: MemberRoleModifyOrmPort
) : MemberLoginFailureUsecase {
    companion object {
        // 계정 비활성화 실패 카운트 기준
        const val DISABLE_COUNT = 4

        // 계정 비활성화 잠금 시간 기준
        const val DISABLE_LOCK_TIME = 15L
    }

    @TXExecute
    override fun disableUserIfFailCountOver(email: String): Boolean {
        val id = memberReadOrmPort.readIdByEmail(email) ?: throw BusinessValidException("존재하지 않는 계정입니다.")
        val failCount =
            memberReadOrmPort.readFailCountById(id) ?: throw BusinessValidException("존재하지 않는 계정입니다.")

        // 비활성화 실패 카운트 기준 초과시 enabled=false 변경
        if (failCount >= DISABLE_COUNT) {
            membersRoleModifyRepository.updateEnabledById(id, false)
        }

        // 실패 카운트 +1
        memberModifyOrmPort.updateFailCountById(id, failCount + 1)
        return failCount >= DISABLE_COUNT
    }

    @TXExecute
    override fun ableUserIfDisableTimeOver(email: String): Boolean {
        val userId = memberReadOrmPort.readIdByEmail(email) ?: throw BusinessValidException("존재하지 않는 계정입니다.")
        val lastFailTime: LocalDateTime =
            memberReadOrmPort.findLastFailTimeById(userId) ?: throw BusinessValidException("존재하지 않는 계정입니다.")

        val isAfterLockTime = lastFailTime.plusMinutes(DISABLE_LOCK_TIME).isBefore(LocalDateTime.now())

        // 비활성화 잠금 시간 지나면 enabled=true, failCount=0로 변경(로그인 시도 가능하도록)
        if (isAfterLockTime) {
            membersRoleModifyRepository.updateEnabledById(userId, true)
            memberModifyOrmPort.updateFailCountById(userId, 0)
        } else {
            // 비활성화 잠금 시간이 지나지 않으면 마지막 실패 시간만 변경(지속적으로 실패시 계정 잠금시간을 늘리기 위하여)
            memberModifyOrmPort.updateLastFailTimeById(userId, LocalDateTime.now())
        }
        return isAfterLockTime
    }
}