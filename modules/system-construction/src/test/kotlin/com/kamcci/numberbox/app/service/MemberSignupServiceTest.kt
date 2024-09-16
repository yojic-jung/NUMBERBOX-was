package com.kamcci.numberbox.app.service

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.util.*

class MemberSignupServiceTest {

    private lateinit var memberSignupService: MemberSignupService

    companion object {
        val memberSignUpDto =
            MemberSignUpDto("test@test.com", "abcd1234!", UUID.fromString("3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"))
        val privateSignUpDto = MemberPrivateSignUpDto("홍길동", "01012345678", "721231")
    }

    @BeforeEach
    fun `초기화`() {
        memberSignupService =
            MemberSignupService(mock(), mock(), mock(), mock(), mock(), mock(), mock(), mock(), mock())
    }

    @Test
    fun `회원 가입 개인정보 미존재 - 성공`() {

    }

    @Test
    fun `회원 가입 개인정보 존재 - 성공`() {

    }

    @Test
    fun `이메일 검증 코드 미존재 - 실패`() {

    }

    @Test
    fun `이메일 검증 코드 유효 시간 지남 - 실패`() {

    }

    @Test
    fun `이메일 검증 코드 불일치 - 실패`() {

    }

    @Test
    fun `중복 이메일 존재 - 실패`() {

    }
}