package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.member.MemberProfileReadOrmPort
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
            memberProfileReadService.readFollowingProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로잉 조회 - 실패(프로필 미존재 회원)`() {
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadOrmPort.readProfileIdByMemberId(memberId)).thenReturn(null)
        assertThrows<BusinessValidException> {
            memberProfileReadService.readFollowingProfileByMemberId(memberId)
        }
    }

    @Test
    fun `팔로워 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.readFollowerProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로워 조회 - 실패(프로필 미존재 회원)`() {
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadOrmPort.readProfileIdByMemberId(memberId)).thenReturn(null)
        assertThrows<BusinessValidException> {
            memberProfileReadService.readFollowerProfileByMemberId(memberId)
        }
    }
}