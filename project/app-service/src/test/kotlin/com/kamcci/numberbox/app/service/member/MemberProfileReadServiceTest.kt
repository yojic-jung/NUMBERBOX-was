package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.*

class MemberProfileReadServiceTest {
    private val memberProfileReadCase: MemberProfileReadCase = mock()

    private val memberProfileReadService: MemberProfileReadService =
        MemberProfileReadService(memberProfileReadCase, mock())

    @Test
    fun `팔로잉 조회 - 성공`() {
        assertDoesNotThrow {
            memberProfileReadService.readFollowingProfileByMemberId(UUID.randomUUID())
        }
    }

    @Test
    fun `팔로잉 조회 - 실패(프로필 미존재 회원)`() {
        // given
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadCase.readProfileIdByMemberId(memberId)).thenReturn(null)
        
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
        val memberId = UUID.randomUUID()
        `when`(memberProfileReadCase.readProfileIdByMemberId(memberId)).thenReturn(null)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberProfileReadService.readFollowerProfileByMemberId(memberId)
        }
        assertThat(exception.msg).isEqualTo(MemberProfileReadService.NOT_EXIST_PROFILE)
    }
}