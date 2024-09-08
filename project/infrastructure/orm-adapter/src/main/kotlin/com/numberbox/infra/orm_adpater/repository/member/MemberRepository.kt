package com.numberbox.infra.orm_adpater.repository.member

import com.numberbox.infra.orm_adpater.entity.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<MemberEntity, UUID> {
    fun findByEmail(email: String): MemberEntity?
}
