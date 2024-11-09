package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRefreshTokenJpaRepository : JpaRepository<MemberRefreshTokenEntity, UUID> {

    fun deleteByToken(token: String)
}
