package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberRoleWriteOrmPort
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberWriteOrmPort
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberReadCase
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberReadCase.Companion.BEFORE_20m_AGO_FAIL_EMAIL
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberReadCase.Companion.DISABLE_MEMBER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberLoginFailureServiceTest {
    companion object {
        const val EMAIL = "test@test.com"
    }

    private val memberReadCase = MockMemberReadCase()
    private val memberWriteOrmPort = MockMemberWriteOrmPort()
    private val membersRoleModifyRepository = MockMemberRoleWriteOrmPort()

    private val memberLoginFailureService =
        MemberLoginFailureService(memberReadCase, memberWriteOrmPort, membersRoleModifyRepository)

    @Test
    fun `실패 카운트 초과시 계정 비활성화 - 성공`() {
        // given
        val memberReadCase = MockMemberReadCase()
        memberReadCase.failCnt = 4
        val memberLoginFailureService =
            MemberLoginFailureService(memberReadCase, memberWriteOrmPort, membersRoleModifyRepository)


        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(EMAIL)

        assertThat(isDisabled).isTrue()
    }

    @Test
    fun `실패 카운트 미초과시 계정 비활성화 진행 안함 - 성공`() {
        val isDisabled = memberLoginFailureService.disableUserIfFailCountOver(EMAIL)

        assertThat(isDisabled).isFalse()
    }

    @Test
    fun `계정 비활성(실패 카운트 미존재) - 실패`() {
        val email = FAIL_EMAIL

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.disableUserIfFailCountOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `계정 비활성화(회원 미존재) - 실패`() {
        val email = DISABLE_MEMBER

        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.disableUserIfFailCountOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화 - 성공`() {
        // given
        val before20mFailEmail = BEFORE_20m_AGO_FAIL_EMAIL

        // when
        val isAbled = memberLoginFailureService.ableUserIfDisableTimeOver(before20mFailEmail)

        assertThat(isAbled).isTrue()
    }

    @Test
    fun `게정 장금 시간 안 지나면 계정 활성화 안함 - 성공`() {
        val isAbled = memberLoginFailureService.ableUserIfDisableTimeOver(EMAIL)

        assertThat(isAbled).isFalse()
    }

    @Test
    fun `게정 장금 시간 지나면 계정 활성화(미존재 계정) - 실패`() {
        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.ableUserIfDisableTimeOver(FAIL_EMAIL)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

    @Test
    fun `게정 장금 활성화(마지막 실패 시간 미존재) - 실패`() {
        // 마지막 실패 시간 미존재 비활성확 계정
        val email = MockMemberReadCase.NONE_FAIL_DISABLE_MEMBER

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberLoginFailureService.ableUserIfDisableTimeOver(email)
        }
        assertThat(exception.msg).isEqualTo(MemberLoginFailureService.NOT_EXIST_USER)
    }

}