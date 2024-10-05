package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.*

class MemberProfileReadServiceTest {
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort = mock()

    private val memberProfileReadService: MemberProfileReadService =
        MemberProfileReadService(memberProfileReadOrmPort, mock())

    @Test
    fun `팔로잉 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.findFollowingProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로잉 조회 - 실패(프로필 미존재 회원)`() {
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadOrmPort.findProfileIdByMemberId(memberId)).thenReturn(null)
        assertThrows<BusinessInValidException> {
            memberProfileReadService.findFollowingProfileByMemberId(memberId)
        }
    }

    @Test
    fun `팔로워 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.findFollowerProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로워 조회 - 실패(프로필 미존재 회원)`() {
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadOrmPort.findProfileIdByMemberId(memberId)).thenReturn(null)
        assertThrows<BusinessInValidException> {
            memberProfileReadService.findFollowerProfileByMemberId(memberId)
        }
    }
}