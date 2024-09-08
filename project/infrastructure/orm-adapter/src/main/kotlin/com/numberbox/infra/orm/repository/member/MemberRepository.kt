package com.numberbox.infra.orm.repository.member

import com.numberbox.infra.orm.entity.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<MemberEntity, UUID> {
    fun findByEmail(email: String): MemberEntity?
}
