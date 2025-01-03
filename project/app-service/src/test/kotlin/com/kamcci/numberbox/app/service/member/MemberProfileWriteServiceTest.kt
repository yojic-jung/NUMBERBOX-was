package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.port.orm.member.MemberProfileWriteOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.service.member.MemberFixture.getMemberProfileImgUpdtDto
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.util.*

class MemberProfileWriteServiceTest {
    private val memberProfileReadOrmPort: MemberProfileReadCase = mock()
    private val memberProfileWriteOrmPort: MemberProfileWriteOrmPort = mock()
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort = mock()

    private val memberProfileWriteService = MemberProfileWriteService(
        memberProfileReadOrmPort,
        memberProfileWriteOrmPort,
        sysGarbageFileWriteOrmPort,
    )

    @Test
    fun `프로필 타입 변경 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val profileType = ProfileType.Etc

        // when
        memberProfileWriteService.updateProfileTypeByMemberId(memberId, profileType)

        // then
        verify(memberProfileWriteOrmPort).updateProfileTypeByMemberId(memberId, profileType)
    }

    @Test
    fun `프로필 이미지 변경 - 성공`() {
        // given
        val updateDto = getMemberProfileImgUpdtDto()
        val profileImgVo = MemberProfileImgVo(1L, UUID.randomUUID(), "path", "name")
        Mockito.`when`(memberProfileReadOrmPort.readProfileImgByMemberId(updateDto.memberId)).thenReturn(profileImgVo)

        // when
        assertDoesNotThrow {
            memberProfileWriteService.updateImgByMemberId(updateDto)
        }
    }

    @Test
    fun `프로필 이미지 변경(이미지 null or empty) - 성공`() {
        // given
        val updateDto = getMemberProfileImgUpdtDto()
        val profileImgVoList = listOf(
            MemberProfileImgVo(1L, UUID.randomUUID(), null, null),
            MemberProfileImgVo(1L, UUID.randomUUID(), "", ""),
            MemberProfileImgVo(1L, UUID.randomUUID(), null, ""),
            MemberProfileImgVo(1L, UUID.randomUUID(), "", null),
            MemberProfileImgVo(1L, UUID.randomUUID(), "123", null),
            MemberProfileImgVo(1L, UUID.randomUUID(), null, "213"),
            MemberProfileImgVo(1L, UUID.randomUUID(), "", "213"),
            MemberProfileImgVo(1L, UUID.randomUUID(), "213", ""),
        )

        for (profileImgVo in profileImgVoList) {
            Mockito.`when`(memberProfileReadOrmPort.readProfileImgByMemberId(updateDto.memberId))
                .thenReturn(profileImgVo)

            // when & then
            assertDoesNotThrow {
                memberProfileWriteService.updateImgByMemberId(updateDto)
            }
        }

    }


    @Test
    fun `프로필 이미지 변경(이미지 빈값) - 성공`() {
        // given
        val updateDto = getMemberProfileImgUpdtDto()
        val profileImgVo = null
        Mockito.`when`(memberProfileReadOrmPort.readProfileImgByMemberId(updateDto.memberId)).thenReturn(profileImgVo)

        // when
        assertDoesNotThrow {
            memberProfileWriteService.updateImgByMemberId(updateDto)
        }
    }

    @Test
    fun `프로필 닉네임 변경 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val nickname = "nickname"

        // when
        memberProfileWriteService.updateNicknameByMemberId(memberId, nickname)

        // then
        verify(memberProfileWriteOrmPort).updateNicknameByMemberId(memberId, nickname)
    }


    @Test
    fun `한글 다운 횟수 변경 - 성공`() {
        // given
        val cnt = 0

        // when
        memberProfileWriteService.updateHwpDownCnt(cnt)

        // then
        verify(memberProfileWriteOrmPort).updateHwpDownCntByMemberId(cnt)
    }
}