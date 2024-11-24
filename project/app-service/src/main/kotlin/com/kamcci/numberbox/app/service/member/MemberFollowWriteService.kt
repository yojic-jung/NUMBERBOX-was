package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberFollowModifyOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteUseCase

@UseCase
class MemberFollowWriteService(
    private val memberFollowModifyOrmPort: MemberFollowModifyOrmPort
) : MemberFollowWriteUseCase {
    @TXExecute
    override fun following(followingId: Long, followerId: Long): Boolean {
        return memberFollowModifyOrmPort.save(followingId, followerId)
    }

    @TXExecute
    override fun cancel(followingId: Long, followerId: Long): Boolean {
        return memberFollowModifyOrmPort.delete(followingId, followerId)
    }
}