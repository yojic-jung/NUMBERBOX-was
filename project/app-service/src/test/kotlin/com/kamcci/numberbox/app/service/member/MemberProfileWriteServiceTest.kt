package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberProfileImgUpdtDto
import com.kamcci.numberbox.app.service.stub.port.orm.member.MockMemberProfileWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.sys.MockSysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID1
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID2
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID3
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID4
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID5
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID6
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID7
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID8
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileReadCase.Companion.FILE_NULL_OR_EMPTY_ID9
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

class MemberProfileWriteServiceTest {
    private val memberProfileWriteService = MemberProfileWriteService(
        MockMemberProfileReadCase(),
        MockMemberProfileWriteOrmPort(),
        MockSysGarbageFileWriteOrmPort(),
    )

    @Test
    fun `프로필 타입 변경 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val profileType = ProfileType.Etc

        // when & then
        assertDoesNotThrow {
            memberProfileWriteService.updateProfileTypeByMemberId(memberId, profileType)
        }
    }

    @Test
    fun `프로필 이미지 변경 - 성공`() {
        // given
        val updateDto = getMemberProfileImgUpdtDto()

        // when & then
        assertDoesNotThrow {
            memberProfileWriteService.updateImgByMemberId(updateDto)
        }
    }

    @Test
    fun `프로필 이미지 변경(이미지 null or empty) - 성공`() {
        // given
        val fileNameNullOrEmptyIdList = listOf(
            FILE_NULL_OR_EMPTY_ID1,
            FILE_NULL_OR_EMPTY_ID2,
            FILE_NULL_OR_EMPTY_ID3,
            FILE_NULL_OR_EMPTY_ID4,
            FILE_NULL_OR_EMPTY_ID5,
            FILE_NULL_OR_EMPTY_ID6,
            FILE_NULL_OR_EMPTY_ID7,
            FILE_NULL_OR_EMPTY_ID8,
            FILE_NULL_OR_EMPTY_ID9,
            UUID.randomUUID()
        )

        for (fileNameNullOrEmptyId in fileNameNullOrEmptyIdList) {
            val updateDto = getMemberProfileImgUpdtDto(fileNameNullOrEmptyId)

            // when & then
            assertDoesNotThrow {
                memberProfileWriteService.updateImgByMemberId(updateDto)
            }
        }

    }


    @Test
    fun `프로필 이미지 변경(이미지 빈값) - 성공`() {
        // given
        val updateDto = getMemberProfileImgUpdtDto(FAIL_MEMBER_ID)

        // when & then
        assertDoesNotThrow {
            memberProfileWriteService.updateImgByMemberId(updateDto)
        }
    }

    @Test
    fun `프로필 닉네임 변경 - 성공`() {
        // given
        val memberId = UUID.randomUUID()
        val nickname = "nickname"

        // when & then
        assertDoesNotThrow {
            memberProfileWriteService.updateNicknameByMemberId(memberId, nickname)
        }
    }


    @Test
    fun `한글 다운 횟수 변경 - 성공`() {
        // given
        val cnt = 0

        // when & then
        assertDoesNotThrow {
            memberProfileWriteService.updateHwpDownCnt(cnt)
        }
    }
}