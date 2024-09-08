package com.numberbox.infra.orm_adpater.repository_impl.member

import com.numberbox.app.domain.member.EmailVerifyCodeSaveDto
import com.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.numberbox.infra.orm_adpater.abstract_code.CommonRepository
import com.numberbox.infra.orm_adpater.factory.member.EmailVerifyCodeFactory
import org.springframework.stereotype.Repository

@Repository
class EmailIDCodeCmdRepoImpl(
    private val emailVerifyCodeFactory: EmailVerifyCodeFactory
) : EmailIDCodeCmdRepository, CommonRepository() {

    override fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean {
        val emailIDCodeEntity = emailVerifyCodeFactory.save(emailVerifyCodeSaveDto)
        em.persist(emailIDCodeEntity)
        return em.contains(emailIDCodeEntity)
    }
}