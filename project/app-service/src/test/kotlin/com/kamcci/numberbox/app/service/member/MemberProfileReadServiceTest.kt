package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberFollowReadCase
import com.kamcci.numberbox.app.service.mock.usecase.member.MockMemberProfileReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class MemberProfileReadServiceTest {
    private val memberProfileReadCase = MockMemberProfileReadCase()

    private val memberProfileReadService =
        MemberProfileReadService(memberProfileReadCase, MockMemberFollowReadCase())

    @Test
    fun `팔로잉 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.readFollowingProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로잉 조회 - 실패(프로필 미존재 회원)`() {
        // given
        val memberId = FAIL_MEMBER_ID

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberProfileReadService.readFollowingProfileByMemberId(memberId)
        }
        assertThat(exception.msg).isEqualTo(MemberProfileReadService.NOT_EXIST_PROFILE)
    }

    @Test
    fun `팔로워 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.readFollowerProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로워 조회 - 실패(프로필 미존재 회원)`() {
        // given
        val memberId = FAIL_MEMBER_ID

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberProfileReadService.readFollowerProfileByMemberId(memberId)
        }
        assertThat(exception.msg).isEqualTo(MemberProfileReadService.NOT_EXIST_PROFILE)
    }
}