package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.orm.member.MemberFollowWriteOrmPort
import com.kamcci.numberbox.app.service.member.MemberFollowWriteService.Companion.NOT_CANCELD
import com.kamcci.numberbox.app.service.member.MemberFollowWriteService.Companion.NOT_FOLLOWED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MemberFollowWriteServiceTest {
    private val memberFollowWriteOrmPort: MemberFollowWriteOrmPort = mock()

    private val memberFollowWriteCase = MemberFollowWriteService(memberFollowWriteOrmPort)

    @Test
    fun `팔로잉 - 성공`() {
        // given
        val followingId = 1L
        val followerId = 2L

        Mockito.`when`(memberFollowWriteOrmPort.save(followingId, followerId)).thenReturn(true)

        // when
        assertDoesNotThrow {
            memberFollowWriteCase.following(followingId, followerId)
        }
    }

    @Test
    fun `팔로잉 - 실패`() {
        // given
        val followingId = 1L
        val followerId = 2L

        Mockito.`when`(memberFollowWriteOrmPort.save(followingId, followerId)).thenReturn(false)

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

        Mockito.`when`(memberFollowWriteOrmPort.delete(followingId, followerId)).thenReturn(1L)

        // when
        assertDoesNotThrow {
            memberFollowWriteCase.cancel(followingId, followerId)
        }
    }

    @Test
    fun `팔로잉 취소 - 실패`() {
        // given
        val followingId = 1L
        val followerId = 2L

        Mockito.`when`(memberFollowWriteOrmPort.delete(followingId, followerId)).thenReturn(0L)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberFollowWriteCase.cancel(followingId, followerId)
        }
        assertThat(exception.msg).isEqualTo(NOT_CANCELD)
    }

}