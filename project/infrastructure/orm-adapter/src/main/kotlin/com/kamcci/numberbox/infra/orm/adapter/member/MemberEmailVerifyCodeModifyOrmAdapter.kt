package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailVerifyCodeSaveDto
import com.kamcci.numberbox.app.port.repository.member.MemberEmailVerifyCodeModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory.makeSaveEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory.makeUpdateEntity
import org.springframework.stereotype.Repository

@Repository
class MemberEmailVerifyCodeModifyOrmAdapter : MemberEmailVerifyCodeModifyOrmPort, BaseRepository() {

    override fun save(memberEmailVerifyCodeSaveDto: MemberEmailVerifyCodeSaveDto): Boolean {
        val emailCodeEntity = em.find(MemberEmailVerifyCodeEntity::class.java, memberEmailVerifyCodeSaveDto.email)
        return if (emailCodeEntity != null) {
            val emailVerifyCodeUpdateEntity = makeUpdateEntity(memberEmailVerifyCodeSaveDto)
            em.merge(emailVerifyCodeUpdateEntity)
            em.contains(emailVerifyCodeUpdateEntity)
        } else {
            val emailVerifyCodeSaveEntity = makeSaveEntity(memberEmailVerifyCodeSaveDto)
            em.persist(emailVerifyCodeSaveEntity)
            em.contains(emailVerifyCodeSaveEntity)
        }
    }
}
