package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.service.member.MemberFixture.getMemberPhoneUpdtDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MemberPrivateWriteServiceTest {

    private val memberPrivateWriteOrmPort: MemberPrivateWriteOrmPort = mock()

    // 타깃
    private val memberPrivateWriteService = MemberPrivateWriteService(memberPrivateWriteOrmPort)

    @Test
    fun `휴대폰 번호 변경 - 성공`() {
        // given
        val phoneUpdtDto = getMemberPhoneUpdtDto()
        Mockito.`when`(memberPrivateWriteOrmPort.updatePhoneNumber(phoneUpdtDto)).thenReturn(1)

        // when
        val isUpdated = memberPrivateWriteService.updatePhoneNumber(phoneUpdtDto)

        // then
        assertThat(isUpdated).isEqualTo(true)
    }

    @Test
    fun `휴대폰 번호 변경 - 실패`() {
        // given
        val phoneUpdtDto = getMemberPhoneUpdtDto()
        Mockito.`when`(memberPrivateWriteOrmPort.updatePhoneNumber(phoneUpdtDto)).thenReturn(0)

        // when
        val isUpdated = memberPrivateWriteService.updatePhoneNumber(phoneUpdtDto)

        // then
        assertThat(isUpdated).isEqualTo(false)
    }
}