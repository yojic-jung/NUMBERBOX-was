package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.infra.orm.entity.member.MemberRefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRefreshTokenRepository : JpaRepository<MemberRefreshTokenEntity, UUID> {

    fun deleteByToken(token: String)
}
