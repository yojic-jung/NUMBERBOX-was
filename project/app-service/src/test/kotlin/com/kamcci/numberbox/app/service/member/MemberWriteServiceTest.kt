package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXIST_EMAIL
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
import org.mockito.Mockito
import org.mockito.Mockito.verify
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
        val signUpDto = getMemberSignupDto(EXIST_EMAIL)
        val privateSignUpDto = null
        val encodedPassword = "encodedPassword"
        val id = UUID.randomUUID()
        val roleList = listOf("USER")

        Mockito.`when`(memberReadCase.existsByEmail(signUpDto.email)).thenReturn(false)
        Mockito.`when`(memberPasswordEncoder.encode(signUpDto.password)).thenReturn(encodedPassword)
        Mockito.`when`(memberWriteOrmPort.save(signUpDto.email, encodedPassword)).thenReturn(id)
        Mockito.`when`(roleReadRepo.readRoleByMemberId(id)).thenReturn(roleList)

        // when
        val signUpResult = memberWriteService.signup(signUpDto, privateSignUpDto)

        // then
        assertThat(signUpResult.uuid).isEqualTo(id)
    }

    @Test
    fun `회원가입 - 실패`() {
        // given
        val signUpDto = getMemberSignupDto()
        val privateSignUpDto = null

        Mockito.`when`(memberReadCase.existsByEmail(signUpDto.email)).thenReturn(true)

        // when
        assertThrows<BusinessInValidException> {
            memberWriteService.signup(signUpDto, privateSignUpDto)
        }
    }

    @Test
    fun `비밀번호 변경 - 성공`() {
        // given
        val updtDto = getMemberPasswdUpdtDto()
        val existPW = updtDto.previousPassword
        val encodedPW = "sdfadf"

        Mockito.`when`(memberReadCase.readPasswordByMemberId(updtDto.memberId)).thenReturn(existPW)
        Mockito.`when`(memberPasswordEncoder.matches(updtDto.previousPassword, existPW)).thenReturn(true)
        Mockito.`when`(memberPasswordEncoder.encode(updtDto.password)).thenReturn(encodedPW)
        Mockito.`when`(memberWriteOrmPort.updatePassword(updtDto.memberId, encodedPW)).thenReturn(1)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(true)
    }

    @Test
    fun `비밀번호 변경(이전 비밀번호 미존재) - 실패`() {
        // given
        val updtDto = getMemberPasswdUpdtDto()

        Mockito.`when`(memberReadCase.readPasswordByMemberId(updtDto.memberId)).thenReturn(null)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 변경(이전 비밀번호 불일치) - 실패`() {
        // given
        val updtDto = getMemberPasswdUpdtDto()
        val existPW = updtDto.previousPassword

        Mockito.`when`(memberReadCase.readPasswordByMemberId(updtDto.memberId)).thenReturn(existPW)
        Mockito.`when`(memberPasswordEncoder.matches(updtDto.previousPassword, existPW)).thenReturn(false)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 변경(update 실패) - 실패`() {
        // given
        val updtDto = getMemberPasswdUpdtDto()
        val existPW = updtDto.previousPassword
        val encodedPW = "sdfadf"

        Mockito.`when`(memberReadCase.readPasswordByMemberId(updtDto.memberId)).thenReturn(existPW)
        Mockito.`when`(memberPasswordEncoder.matches(updtDto.previousPassword, existPW)).thenReturn(true)
        Mockito.`when`(memberPasswordEncoder.encode(updtDto.password)).thenReturn(encodedPW)
        Mockito.`when`(memberWriteOrmPort.updatePassword(updtDto.memberId, encodedPW)).thenReturn(0)

        // when
        val isUpdated = memberWriteService.updatePassword(updtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }

    @Test
    fun `비밀번호 확인 - 성공`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto()
        val encodedPW = "encodedPW"

        Mockito.`when`(memberReadCase.readPasswordByMemberId(confirmDto.memberId)).thenReturn(encodedPW)
        Mockito.`when`(memberPasswordEncoder.matches(confirmDto.password, encodedPW)).thenReturn(true)

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(true)
    }

    @Test
    fun `비밀번호 확인(이전 비밀번호 미존재) - 실패`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto()

        Mockito.`when`(memberReadCase.readPasswordByMemberId(confirmDto.memberId)).thenReturn(null)

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(false)
    }

    @Test
    fun `비밀번호 확인(비밀번호 불일치) - 실패`() {
        // given
        val confirmDto = getMemberPasswdConfirmDto()
        val encodedPW = "encodedPW"

        Mockito.`when`(memberReadCase.readPasswordByMemberId(confirmDto.memberId)).thenReturn(encodedPW)
        Mockito.`when`(memberPasswordEncoder.matches(confirmDto.password, encodedPW)).thenReturn(false)

        // when
        val isEqual = memberWriteService.confirmPassword(confirmDto)

        // then
        assertThat(isEqual).isEqualTo(false)
    }

    @Test
    fun `임시 비밀번호 발급 - 성공`() {
        // given
        val ids = listOf(UUID.randomUUID(), UUID.randomUUID())

        // when
        memberWriteService.updateTmpPassword(ids)

        // then
        verify(memberWriteOrmPort).updatePassword(ids, null)
    }
}