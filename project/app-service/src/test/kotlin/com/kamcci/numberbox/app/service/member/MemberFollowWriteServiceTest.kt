package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.member.MemberFollowWriteService.Companion.NOT_CANCELD
import com.kamcci.numberbox.app.service.member.MemberFollowWriteService.Companion.NOT_FOLLOWED
import com.kamcci.numberbox.app.service.mock.port.orm.member.MockMemberFollowWriteOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MemberFollowWriteServiceTest {
    private val memberFollowWriteOrmPort = MockMemberFollowWriteOrmPort()
    private val memberFollowWriteCase = MemberFollowWriteService(memberFollowWriteOrmPort)

    @Test
    fun `팔로잉 - 성공`() {
        // given
        val followingId = 1L
        val followerId = 2L

        // when
        assertDoesNotThrow {
            memberFollowWriteCase.following(followingId, followerId)
        }
    }

    @Test
    fun `팔로잉 - 실패`() {
        // given
        val followingId = FAIL_ID
        val followerId = 2L

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberFollowWriteCase.following(followingId, followerId)
        }
        assertThat(exception.msg).isEqualTo(NOT_FOLLOWED)
    }

    @Test
    fun `팔로잉 취소 - 성공`() {
        // given
        val followingId = 1L
        val followerId = 2L

        // when
        assertDoesNotThrow {
            memberFollowWriteCase.cancel(followingId, followerId)
        }
    }

    @Test
    fun `팔로잉 취소 - 실패`() {
        // given
        val followingId = FAIL_ID
        val followerId = 2L

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberFollowWriteCase.cancel(followingId, followerId)
        }
        assertThat(exception.msg).isEqualTo(NOT_CANCELD)
    }

}