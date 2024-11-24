package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperWriteOrmPort
import com.kamcci.numberbox.app.port.orm.math.MathContentsWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberRoleWriteOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberDropCase
import java.util.*

@UseCase
class MemberDropService(
    private val memberWriteOrmPort: MemberWriteOrmPort,
    private val memberRoleWriteOrmPort: MemberRoleWriteOrmPort,
    private val memberPrivateWriteOrmPort: MemberPrivateWriteOrmPort,
    private val mathContentsOrmPort: MathContentsWriteOrmPort,
    private val mathDocsPaperWriteOrmPort: MathDocsPaperWriteOrmPort,
) : MemberDropCase {

    @TXExecute
    override fun drop(memberId: UUID) {
        // 1. 개인 정보 파기
        memberPrivateWriteOrmPort.updatePrivateToNull(memberId)

        // 2. 사용자 제작 문제 삭제(soft delete)
        mathContentsOrmPort.updateContentsClassifyType(memberId, ContentsClassifyType.Deleted)

        // 3. 학습지 생성내역 삭제(soft delete)
        mathDocsPaperWriteOrmPort.delete(memberId)

        // 4. 최종 탈퇴 처리(human_status=3(탈퇴회원), enabled=false)
        memberWriteOrmPort.drop(memberId)
        memberRoleWriteOrmPort.updateEnabledById(memberId, false)
    }

}
