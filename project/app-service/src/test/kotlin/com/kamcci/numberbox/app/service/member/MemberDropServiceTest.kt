package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberRoleWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.util.*

class MemberDropServiceTest {
    private val memberWriteOrmPort: MemberWriteOrmPort = mock()
    private val memberRoleWriteOrmPort: MemberRoleWriteOrmPort = mock()
    private val memberPrivateWriteOrmPort: MemberPrivateWriteOrmPort = mock()
    private val mathContentsOrmPort: MathContentsWriteOrmPort = mock()
    private val mathDocsPaperWriteOrmPort: MathDocsPaperWriteOrmPort = mock()

    private val memberDropService = MemberDropService(
        memberWriteOrmPort,
        memberRoleWriteOrmPort,
        memberPrivateWriteOrmPort,
        mathContentsOrmPort,
        mathDocsPaperWriteOrmPort
    )

    @Test
    fun `회원 탈퇴 - 성공`() {
        // given
        val memberId = UUID.randomUUID()

        // when
        memberDropService.drop(memberId)

        // then
        verify(memberPrivateWriteOrmPort).updatePrivateToNull(memberId)
        verify(mathContentsOrmPort).updateContentsClassifyType(memberId, ContentsClassifyType.Deleted)
        verify(mathDocsPaperWriteOrmPort).delete(memberId)
        verify(memberWriteOrmPort).drop(memberId)
        verify(memberRoleWriteOrmPort).updateEnabledById(memberId, false)
    }
}