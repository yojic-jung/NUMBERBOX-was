package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberFollowWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase

@UseCase
class MemberFollowWriteService(
    private val memberFollowWriteOrmPort: MemberFollowWriteOrmPort
) : MemberFollowWriteCase {
    @TXExecute
    override fun following(followingId: Long, followerId: Long): Boolean {
        return memberFollowWriteOrmPort.save(followingId, followerId)
    }

    @TXExecute
    override fun cancel(followingId: Long, followerId: Long): Boolean {
        return memberFollowWriteOrmPort.delete(followingId, followerId)
    }
}