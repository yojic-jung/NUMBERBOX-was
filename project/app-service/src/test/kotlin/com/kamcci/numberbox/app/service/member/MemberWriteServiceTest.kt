package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_STRING
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPasswdConfirmDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPasswdUpdtDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberPrivateSignUpDto
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberSignupDto
import com.kamcci.numberbox.app.service.stub.port.etc.MockMemberPasswordEncoder
import com.kamcci.numberbox.app.service.stub.port.orm.member.*
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberReadCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class MemberWriteServiceTest {
    // 모킹
    private val memberWriteOrmPort = MockMemberWriteOrmPort()
    private val memberReadCase = MockMemberReadCase()
    private val memberPasswordEncoder = MockMemberPasswordEncoder()
    private val roleModifyRepo = MockMemberRoleWriteOrmPort()
    private val roleReadRepo = MockMemberRoleReadOrmPort()
    private val profileModifyOrmPort = MockMemberProfileWriteOrmPort()
    private val privateModifyRepo = MockMemberPrivateWriteOrmPort()

    // 타깃
    private val memberWriteService = MemberWriteService(
        memberWriteOrmPort,
        memberReadCase,
        memberPasswordEncoder,
        roleModifyRepo,
        roleReadRepo,
        profileModifyOrmPort,
        privateModifyRepo,
    )

    @Test
    fun `회원가입(개인정보 존재) - 성공`() {
        // given
        val notExistEmail = EXIST_EMAIL.reversed()
        val signUpDto = getMemberSignupDto(notExistEmail)
        val privateSignUpDto = getMemberPrivateSignUpDto()

        // when
        val signUpResult = memberWriteService.signup(signUpDto, privateSignUpDto)

        // then
        assertThat(signUpResult.email).isEqualTo(signUpDto.email)
    }

    @Test
    fun `회원가입(개인정보 미존재) - 성공`() {
        // given
        val notExistEmail = EXIST_EMAIL.reversed()
        val signUpDto = getMemberSignupDto(notExistEmail)
        val privateSignUpDto = null

        // when
        val signUpResult = memberWriteService.signup(signUpDto, privateSignUpDto)

        // then
        assertThat(signUpResult.email).isEqualTo(signUpDto.email)
    }

    @Test
    fun `회원가입 - 실패(이미 존재하는 이메일)`() {
        // given
        val signUpDto = getMemberSignupDto(EXIST_EMAIL)
        val privateSignUpDto = null


        // when
        assertThrows<BusinessInValidException> {
            memberWriteService.signup(signUpDto, privateSignUpDto)
        }
    }

    @Test
    fun `비밀번호 변경 - 성공`() {
        // given
        val updtDto = getMemberPasswdUpdtDto()

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(true)
    }

    @Test
    fun `비밀번호 변경(이전 비밀번호 미존재) - 실패`() {
        // given
        val updtDto = getMemberPasswdUpdtDto(memberId = FAIL_MEMBER_ID)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 변경(이전 비밀번호 불일치) - 실패`() {
        // given
        val updtDto = getMemberPasswdUpdtDto(prevPW = FAIL_STRING)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 변경(update 실패) - 실패`() {
        // given
        val mockMemberWriteOrmPort = MockMemberWriteOrmPort()
        val memberWriteService = MemberWriteService(
            mockMemberWriteOrmPort,
            memberReadCase,
            memberPasswordEncoder,
            roleModifyRepo,
            roleReadRepo,
            profileModifyOrmPort,
            privateModifyRepo,
        )
        mockMemberWriteOrmPort.isUpdateFail = true

        // when
        val isUpdated = memberWriteService.updatePassword(getMemberPasswdUpdtDto())

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 확인 - 성공`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto()

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(true)
    }

    @Test
    fun `비밀번호 확인(이전 비밀번호 미존재) - 실패`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto(FAIL_MEMBER_ID)

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(false)
    }

    @Test
    fun `비밀번호 확인(비밀번호 불일치) - 실패`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto(pw = FAIL_STRING)

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(false)
    }

    @Test
    fun `임시 비밀번호 발급 - 성공`() {
        // given
        val mockMemberWriteOrmPort = MockMemberWriteOrmPort()
        val memberWriteService = MemberWriteService(
            mockMemberWriteOrmPort,
            memberReadCase,
            memberPasswordEncoder,
            roleModifyRepo,
            roleReadRepo,
            profileModifyOrmPort,
            privateModifyRepo,
        )

        val ids = listOf(UUID.randomUUID(), UUID.randomUUID())

        // when
        memberWriteService.updateTmpPassword(ids)

        // then
        assertThat(mockMemberWriteOrmPort.executeCnt).isOne()
    }
}