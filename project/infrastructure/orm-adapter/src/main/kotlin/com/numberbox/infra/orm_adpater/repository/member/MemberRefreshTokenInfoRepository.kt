package com.numberbox.infra.orm_adpater.repository.member

import com.numberbox.infra.orm_adpater.entity.member.MemberRefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRefreshTokenInfoRepository : JpaRepository<MemberRefreshTokenEntity, Long> {
    fun findUserUniqIdByToken(token: String): UUID?
}
