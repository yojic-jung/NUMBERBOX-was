package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.member.MemberRoleWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.service.member.MemberLoginFailureService.Companion.DISABLE_COUNT
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import java.time.LocalDateTime
import java.util.*

class MemberLoginFailureServiceTest {
    private val memberReadCase: MemberReadCase = mock()
    private val memberWriteOrmPort: MemberWriteOrmPort = mock()
    private val membersRoleModifyRepository: MemberRoleWriteOrmPort = mock()

    private val memberLoginFailureService =
        MemberLoginFailureService(memberReadCase, memberWriteOrmPort, membersRoleModifyRepository)

    @Test
    fun `실패 카운트 초과시 계정 비활성화 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadCase.readFailCountById(any())).thenReturn(DISABLE_COUNT + 1)

        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(email)

        assertThat(isDisabled).isTrue()
    }

    @Test
    fun `실패 카운트 미초과시 계정 비활성화 진행 안함 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadCase.readFailCountById(any())).thenReturn(DISABLE_COUNT - 1)

        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(email)

        assertThat(isDisabled).isFalse()
    }

    @Test
    fun `계정 비활성(실패 카운트 미존재) - 실패`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadCase.readFailCountById(any())).thenReturn(null)

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.disableUserIfFailCountOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `계정 비활성화(회원 미존재) - 실패`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(email)).thenReturn(null)

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.disableUserIfFailCountOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadCase.readLastFailTimeById(any())).thenReturn(
            LocalDateTime.now().minusMinutes(
                MemberLoginFailureService.DISABLE_LOCK_TIME + 1L
            )
        )

        val isAbled = memberLoginFailureService.ableUserIfDisableTimeOver(email)

        assertThat(isAbled).isTrue()
    }

    @Test
    fun `게정 장금 시간 안 지나면 계정 활성화 안함 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadCase.readLastFailTimeById(any())).thenReturn(LocalDateTime.now())

        val isAbled = memberLoginFailureService.ableUserIfDisableTimeOver(email)

        assertThat(isAbled).isFalse()
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화(미존재 계정) - 실패`() {
        val email = "test"
        Mockito.`when`(memberReadCase.readIdByEmail(email)).thenReturn(null)

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.ableUserIfDisableTimeOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `게정 장금 활성화(마지막 실패 시간 미존재) - 실패`() {
        val email = "test"
        val userId = UUID.randomUUID()
        Mockito.`when`(memberReadCase.readIdByEmail(email)).thenReturn(userId)
        Mockito.`when`(memberReadCase.readLastFailTimeById(userId)).thenReturn(null)

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.ableUserIfDisableTimeOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

}