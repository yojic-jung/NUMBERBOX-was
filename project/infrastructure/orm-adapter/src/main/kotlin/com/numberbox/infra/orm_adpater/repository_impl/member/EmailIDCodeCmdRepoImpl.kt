package com.numberbox.infra.orm_adpater.repository_impl.member

import com.numberbox.app.domain.member.EmailVerifyCodeSaveDto
import com.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.numberbox.infra.orm_adpater.abstract_code.CommonRepository
import com.numberbox.infra.orm_adpater.constructor.member.EmailIDCodeConstructor
import org.springframework.stereotype.Repository

@Repository
class EmailIDCodeCmdRepoImpl(
    private val emailIDCodeConstructor: EmailIDCodeConstructor
) : EmailIDCodeCmdRepository, CommonRepository() {

    override fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean {
        val emailIDCodeEntity = emailIDCodeConstructor.save(emailVerifyCodeSaveDto)
        em.persist(emailIDCodeEntity)
        return em.contains(emailIDCodeEntity)
    }
}