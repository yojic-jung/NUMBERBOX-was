package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberFollowWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase

@UseCase
class MemberFollowWriteService(
    private val memberFollowWriteOrmPort: MemberFollowWriteOrmPort
) : MemberFollowWriteCase {
    companion object {
        const val NOT_FOLLOWED = "팔로잉이 되지 않앟습니다."
        const val NOT_CANCELD = "팔로우 취소가 되지 않았습니다."
    }

    @TXExecute
    override fun following(followingId: Long, followerId: Long) {
        memberFollowWriteOrmPort.save(followingId, followerId).let {
            if (!it) throw BusinessInValidException(NOT_FOLLOWED)
        }
    }

    @TXExecute
    override fun cancel(followingId: Long, followerId: Long) {
        memberFollowWriteOrmPort.delete(followingId, followerId).let {
            if (it == 0L) throw BusinessInValidException(NOT_CANCELD)
        }
    }
}