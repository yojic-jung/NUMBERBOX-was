package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.service.stub.port.orm.docs.MockMathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.math.MockMathContentsWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.member.MockMemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.member.MockMemberRoleWriteOrmPort
import com.kamcci.numberbox.app.service.stub.port.orm.member.MockMemberWriteOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class MemberDropServiceTest {
    lateinit var mockMemberWriteOrmPort: MockMemberWriteOrmPort
    lateinit var memberDropService: MemberDropService

    @BeforeEach
    fun `테스트 스텁 초기화`() {
        mockMemberWriteOrmPort = MockMemberWriteOrmPort()
        memberDropService = MemberDropService(
            mockMemberWriteOrmPort,
            MockMemberRoleWriteOrmPort(),
            MockMemberPrivateWriteOrmPort(),
            MockMathContentsWriteOrmPort(),
            MockMathDocsPaperWriteOrmPort()
        )
    }

    @Test
    fun `회원 탈퇴 - 성공`() {
        // given
        val memberId = UUID.randomUUID()

        // when
        memberDropService.drop(memberId)

        // then
        assertThat(mockMemberWriteOrmPort.executeCnt).isEqualTo(1)
    }
}