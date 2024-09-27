package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase

@UseCase
class MemberProfileReadService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort
) : MemberProfileReadUseCase {
    override fun findByMemberId() {
        memberProfileReadOrmPort.findByMemberId()
    }

    override fun findProfileImgByMemberId() {
        TODO("Not yet implemented")
    }
}