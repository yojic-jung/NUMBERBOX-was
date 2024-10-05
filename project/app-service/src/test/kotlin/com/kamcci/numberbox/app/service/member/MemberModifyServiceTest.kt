package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo.SignUpResultMSg.EXIST_EMAIL_MSG
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo.SignUpResultMSg.SUCCESS_MSG
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MemberModifyServiceTest {
    private val memberReadOrmPort: MemberReadOrmPort = mock()
    private val memberModifyService: MemberModifyService =
        MemberModifyService(mock(), memberReadOrmPort, mock(), mock(), mock(), mock(), mock())

    @Test
    fun `회원가입 - 성공`() {
        // given
        val signUpDto = MemberSignUpDto("", "")
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(false)

        // when
        val signUpResult = memberModifyService.signup(signUpDto, null)

        // then
        assertThat(signUpResult.isSuccess).isTrue()
        assertThat(signUpResult.messageType).isEqualTo(SUCCESS_MSG)
    }

    @Test
    fun `회원가입 - 실패(중복 이메일 존재)`() {
        // given
        val signUpDto = MemberSignUpDto("", "")
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(true)

        // when
        val signUpResult = memberModifyService.signup(signUpDto, null)

        // then
        assertThat(signUpResult.isSuccess).isFalse()
        assertThat(signUpResult.messageType).isEqualTo(EXIST_EMAIL_MSG)
    }
}