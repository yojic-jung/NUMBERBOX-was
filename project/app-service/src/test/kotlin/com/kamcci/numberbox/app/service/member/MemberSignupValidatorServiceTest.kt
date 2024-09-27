package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo.SignUpResultMSg.*
import com.kamcci.numberbox.app.port.repository.member.MemberEmailVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.*

class MemberSignupValidatorServiceTest {

    private val memberEmailVerifyCodeReadOrmPort: MemberEmailVerifyCodeReadOrmPort = mock()
    private val memberReadOrmPort: MemberReadOrmPort = mock()
    private val memberSignupValidator =
        MemberSignupValidatorService(memberEmailVerifyCodeReadOrmPort, memberReadOrmPort)

    companion object {
        const val EMAIL = "test@test.com"
        const val PW = "abcd1234!"
        const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"

        val signUpDto =
            MemberSignUpDto(EMAIL, PW, UUID.fromString(VERIFY_CODE))
    }


    @Test
    fun `이메일 검증 코드 미존재 - 실패`() {
        `when`(memberEmailVerifyCodeReadOrmPort.findByEmail(signUpDto.email)).thenReturn(null)
        // when & then
        assertThrows<BusinessInValidException> { memberSignupValidator.validate(signUpDto) }
    }

    @Test
    fun `이메일 검증 코드 유효 시간 지남 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(181)
        )
        `when`(memberEmailVerifyCodeReadOrmPort.findByEmail(signUpDto.email)).thenReturn(memberEmailVerifyCodeVo)

        // when
        val resultVo = memberSignupValidator.validate(signUpDto)!!

        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(EXPIRED_MSG)
    }

    @Test
    fun `이메일 검증 코드 불일치 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0",
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberEmailVerifyCodeReadOrmPort.findByEmail(signUpDto.email)).thenReturn(memberEmailVerifyCodeVo)

        // when
        val resultVo = memberSignupValidator.validate(signUpDto)!!

        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(NOT_MATCH_CODE_MSG)
    }

    @Test
    fun `중복 이메일 존재 - 실패`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberEmailVerifyCodeReadOrmPort.findByEmail(signUpDto.email)).thenReturn(memberEmailVerifyCodeVo)
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(true)

        // when
        val resultVo = memberSignupValidator.validate(signUpDto)!!

        assertThat(resultVo.isSuccess).isFalse()
        assertThat(resultVo.messageType).isEqualTo(EXIST_EMAIL_MSG)
    }

    @Test
    fun `회원가입 양식 유효성 검사 - 성공`() {
        // given
        val memberEmailVerifyCodeVo = MemberEmailVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberEmailVerifyCodeReadOrmPort.findByEmail(signUpDto.email)).thenReturn(memberEmailVerifyCodeVo)
        `when`(memberReadOrmPort.existsByEmail(signUpDto.email)).thenReturn(false)

        // when
        val resultVo = memberSignupValidator.validate(signUpDto)

        assertThat(resultVo).isNull()
    }
}