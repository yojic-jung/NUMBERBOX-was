package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.*

class MemberVerifyCodeReadServiceTest {

    private val memberVerifyCodeReadOrmPort: MemberVerifyCodeReadOrmPort = mock()
    private val memberVerifyCodeReadUseCase =
        MemberVerifyCodeReadService(memberVerifyCodeReadOrmPort)

    companion object {
        const val EMAIL = "test@test.com"
        const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"

        val signUpDto =
            MemberVerifyCodeDto(EMAIL, UUID.fromString(VERIFY_CODE), VerifyCodeType.SignUp)
    }


    @Test
    fun `인증 코드 미존재 - 실패`() {
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, signUpDto.verifyCodeType))
            .thenReturn(null)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(MemberVerifyCodeReadService.NOT_EXIST_CODE)
    }

    @Test
    fun `인증 검증 코드 만료 시간 지남 - 실패`() {
        // given
        val memberVerifyCodeVo = MemberVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(181)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp))
            .thenReturn(memberVerifyCodeVo)

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(MemberVerifyCodeReadService.EXPIRED_CODE)
    }

    @Test
    fun `인증 코드 불일치 - 실패`() {
        // given
        val memberVerifyCodeVo = MemberVerifyCodeVo(
            verifyCode = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0",
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberVerifyCodeVo
        )

        // when & then
        val exception = assertThrows<BusinessInValidException> {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
        assertThat(exception.msg).isEqualTo(MemberVerifyCodeReadService.NOT_MATCHED_CODE)
    }

    @Test
    fun `인증 유효성 검사 - 성공`() {
        // given
        val memberVerifyCodeVo = MemberVerifyCodeVo(
            verifyCode = VERIFY_CODE,
            sysCreateTime = LocalDateTime.now().minusSeconds(180)
        )
        `when`(memberVerifyCodeReadOrmPort.readByEmailAndCodeType(signUpDto.email, VerifyCodeType.SignUp)).thenReturn(
            memberVerifyCodeVo
        )

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeReadUseCase.validate(signUpDto)
        }
    }
}