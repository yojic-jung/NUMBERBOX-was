package com.kamcci.numberbox.infra.orm.repository.member

import com.kamcci.numberbox.app.member.EmailVerifyCodeSaveDto
import com.kamcci.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import com.kamcci.numberbox.infra.orm.factory.member.MemberEmailVerifyCodeFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EmailIDCodeCmdRepoImpl(
    private val memberEmailVerifyCodeFactory: MemberEmailVerifyCodeFactory,
) : EmailIDCodeCmdRepository, BaseRepository() {

    override fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean {
        val emailIDCodeEntity = memberEmailVerifyCodeFactory.save(emailVerifyCodeSaveDto)
        val alradyEntity = em.find(MemberEmailVerifyCodeEntity::class.java, emailVerifyCodeSaveDto.email)
        if (alradyEntity != null) {
            alradyEntity.apply {
                verifyCode = emailVerifyCodeSaveDto.verifyCode
                sysCreateTime = LocalDateTime.now()
            }
        } else {
            em.persist(emailIDCodeEntity)
        }

        return em.contains(emailIDCodeEntity)
    }
}
