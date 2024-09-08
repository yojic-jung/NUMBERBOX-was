package com.numberbox.infra.orm.repository.member

import com.numberbox.app.domain.member.EmailVerifyCodeSaveDto
import com.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.numberbox.infra.orm.base.BaseRepository
import com.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory
import org.springframework.stereotype.Repository

@Repository
class EmailIDCodeCmdRepoImpl(
    private val memberEmailVerifyCodeFactory: MemberEmailVerifyCodeFactory,
) : EmailIDCodeCmdRepository, BaseRepository() {

    override fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean {
        val emailIDCodeEntity = memberEmailVerifyCodeFactory.save(emailVerifyCodeSaveDto)
        em.persist(emailIDCodeEntity)
        return em.contains(emailIDCodeEntity)
    }
}
