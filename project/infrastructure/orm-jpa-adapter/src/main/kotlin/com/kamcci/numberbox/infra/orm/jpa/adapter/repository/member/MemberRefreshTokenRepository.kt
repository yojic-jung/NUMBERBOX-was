package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheManagerNames.REDIS_2WEEK_CACHE_MANAGER_BEAN
import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Repository

@Repository
class MemberRefreshTokenRepository : BaseRepository() {

    fun save(memberRefreshTokenEntity: MemberRefreshTokenEntity): Long {
        em.persist(memberRefreshTokenEntity)
        return memberRefreshTokenEntity.id
    }


    @CacheEvict(
        cacheManager = REDIS_2WEEK_CACHE_MANAGER_BEAN,
        cacheNames = [CacheNames.REFRESH_TOKEN],
        key = "#token"
    )
    fun deleteByToken(token: String): Long {
        return queryFactory.delete(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
            .execute()
    }
}
