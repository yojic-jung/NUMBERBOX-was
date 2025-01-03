package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberRoleWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import java.time.LocalDateTime

@UseCase
class MemberLoginFailureService(
    private val memberReadCase: MemberReadCase,
    private val memberWriteOrmPort: MemberWriteOrmPort,
    private val membersRoleModifyRepository: MemberRoleWriteOrmPort
) : MemberLoginFailureUseCase {
    companion object {
        // 계정 비활성화 실패 카운트 기준
        const val DISABLE_COUNT = 4

        // 계정 비활성화 잠금 시간 기준
        const val DISABLE_LOCK_TIME = 15L

        const val NOT_EXIST_USER = "존재하지 않는 계정입니다."
    }

    @TXExecute
    override fun disableUserIfFailCountOver(email: String): Boolean {
        val id = memberReadCase.readIdByEmail(email) ?: throw BusinessInValidException(NOT_EXIST_USER)
        val failCount =
            memberReadCase.readFailCountById(id) ?: throw BusinessInValidException(NOT_EXIST_USER)

        // 비활성화 실패 카운트 기준 초과시 enabled=false 변경
        if (failCount >= DISABLE_COUNT) {
            membersRoleModifyRepository.updateEnabledById(id, false)
        }

        // 실패 카운트 +1
        memberWriteOrmPort.updateFailCountById(id, failCount + 1)
        return failCount >= DISABLE_COUNT
    }

    @TXExecute
    override fun ableUserIfDisableTimeOver(email: String): Boolean {
        val userId = memberReadCase.readIdByEmail(email) ?: throw BusinessInValidException(NOT_EXIST_USER)
        val lastFailTime: LocalDateTime =
            memberReadCase.readLastFailTimeById(userId) ?: throw BusinessInValidException(NOT_EXIST_USER)

        val isAfterLockTime = lastFailTime.plusMinutes(DISABLE_LOCK_TIME).isBefore(LocalDateTime.now())

        // 비활성화 잠금 시간 지나면 enabled=true, failCount=0로 변경(로그인 시도 가능하도록)
        if (isAfterLockTime) {
            membersRoleModifyRepository.updateEnabledById(userId, true)
            memberWriteOrmPort.updateFailCountById(userId, 0)
        } else {
            // 비활성화 잠금 시간이 지나지 않으면 마지막 실패 시간만 변경(지속적으로 실패시 계정 잠금시간을 늘리기 위하여)
            memberWriteOrmPort.updateLastFailTimeById(userId, LocalDateTime.now())
        }
        return isAfterLockTime
    }
}