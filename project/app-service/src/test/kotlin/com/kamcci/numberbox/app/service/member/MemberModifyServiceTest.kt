package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.port.orm.member.MemberReadOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberRoleReadOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

class MemberModifyServiceTest {
    private val memberReadOrmPort: MemberReadOrmPort = mock()
    private val roleReadRepo: MemberRoleReadOrmPort = mock()
    private val memberModifyService: MemberWriteService =
        MemberWriteService(mock(), memberReadOrmPort, mock(), mock(), mock(), roleReadRepo, mock(), mock())

    @Test
    fun `회원가입 - 성공`() {
        // given
        val signUpDto = MemberSignUpDto("", "")
        val roleList = listOf("USER")
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(false)
        `when`(roleReadRepo.readRoleByMemberId(any())).thenReturn(roleList)

        // when & then
        assertDoesNotThrow {
            memberModifyService.signup(signUpDto, null)
        }
    }

    @Test
    fun `회원가입 - 실패(중복 이메일 존재)`() {
        // given
        val signUpDto = MemberSignUpDto("", "")
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(true)

        // when & then
        assertThrows<BusinessValidException> {
            memberModifyService.signup(signUpDto, null)
        }
    }
}