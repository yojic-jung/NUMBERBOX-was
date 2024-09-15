package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.domain.dto.member.EmailVerifyCodeSaveDto
import com.kamcci.numberbox.app.repository.member.EmailVerifyCodeModifyRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory.makeSaveEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory.makeUpdateEntity
import org.springframework.stereotype.Repository

@Repository
class EmailVerifyCodeModifyRepoImpl : EmailVerifyCodeModifyRepository, BaseRepository() {

    override fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean {
        val emailCodeEntity = em.find(MemberEmailVerifyCodeEntity::class.java, emailVerifyCodeSaveDto.email)
        return if (emailCodeEntity != null) {
            val emailIDCodeUpdateEntity = makeUpdateEntity(emailVerifyCodeSaveDto)
            em.merge(emailIDCodeUpdateEntity)
            em.contains(emailIDCodeUpdateEntity)
        } else {
            val emailIDCodeSaveEntity = makeSaveEntity(emailVerifyCodeSaveDto)
            em.contains(emailIDCodeSaveEntity)
        }
    }
}
