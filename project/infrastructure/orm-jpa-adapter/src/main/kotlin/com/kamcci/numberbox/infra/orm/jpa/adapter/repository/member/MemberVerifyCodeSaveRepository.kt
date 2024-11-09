package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeSaveOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberVerifyCodeEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberVerifyCodeFactory.makeSaveEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberVerifyCodeFactory.makeUpdateEntity
import org.springframework.stereotype.Repository

@Repository
class MemberVerifyCodeSaveRepository : MemberVerifyCodeSaveOrmPort, BaseRepository() {

    override fun save(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto): Boolean {
        val emailCodeEntity = em.find(MemberVerifyCodeEntity::class.java, memberVerifyCodeSaveDto.email)
        return if (emailCodeEntity != null) {
            val emailVerifyCodeUpdateEntity = makeUpdateEntity(memberVerifyCodeSaveDto)
            em.merge(emailVerifyCodeUpdateEntity)
            em.contains(emailVerifyCodeUpdateEntity)
        } else {
            val emailVerifyCodeSaveEntity = makeSaveEntity(memberVerifyCodeSaveDto)
            em.persist(emailVerifyCodeSaveEntity)
            em.contains(emailVerifyCodeSaveEntity)
        }
    }
}
