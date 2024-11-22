package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.member.MemberModifyOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberReadOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberRoleModifyOrmPort
import com.kamcci.numberbox.app.service.member.MemberLoginFailureService.Companion.DISABLE_COUNT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import java.time.LocalDateTime
import java.util.*

class MemberLoginFailureServiceTest {
    private val memberReadOrmPort: MemberReadOrmPort = mock()
    private val memberModifyOrmPort: MemberModifyOrmPort = mock()
    private val membersRoleModifyRepository: MemberRoleModifyOrmPort = mock()

    private val memberLoginFailureService =
        MemberLoginFailureService(memberReadOrmPort, memberModifyOrmPort, membersRoleModifyRepository)

    @Test
    fun `실패 카운트 초과시 계정 비활성화 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadOrmPort.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadOrmPort.readFailCountById(any())).thenReturn(DISABLE_COUNT + 1)

        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(email)

        assertThat(isDisabled).isTrue()
    }

    @Test
    fun `실패 카운트 미초과시 계정 비활성화 진행 안함 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadOrmPort.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadOrmPort.readFailCountById(any())).thenReturn(DISABLE_COUNT - 1)

        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(email)

        assertThat(isDisabled).isFalse()
    }

    @Test
    fun `실패 카운트 초과시 계정 비활성화 - 실패`() {
        val email = "test"
        Mockito.`when`(memberReadOrmPort.readIdByEmail(email)).thenReturn(null)

        assertThrows<BusinessValidException> {
            memberLoginFailureService.disableUserIfFailCountOver(email)
        }
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화 - 성공`() {
        val email = "test"
        Mockito.`when`(memberReadOrmPort.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadOrmPort.readLastFailTimeById(any())).thenReturn(
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
        Mockito.`when`(memberReadOrmPort.readIdByEmail(any())).thenReturn(UUID.randomUUID())
        Mockito.`when`(memberReadOrmPort.readLastFailTimeById(any())).thenReturn(LocalDateTime.now())

        val isAbled = memberLoginFailureService.ableUserIfDisableTimeOver(email)

        assertThat(isAbled).isFalse()
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화 - 실패(미존재 계정)`() {
        val email = "test"
        Mockito.`when`(memberReadOrmPort.readIdByEmail(email)).thenReturn(null)

        assertThrows<BusinessValidException> {
            memberLoginFailureService.ableUserIfDisableTimeOver(email)
        }
    }

}