package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import java.util.*

@UseCase
class MemberProfileReadService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort
) : MemberProfileReadUseCase {
    override fun findByMemberId(memberId: UUID): MemberProfileVo? {
        return memberProfileReadOrmPort.findByMemberId(memberId)
    }

    override fun findProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        return memberProfileReadOrmPort.findProfileImgByMemberId(memberId)
    }
}