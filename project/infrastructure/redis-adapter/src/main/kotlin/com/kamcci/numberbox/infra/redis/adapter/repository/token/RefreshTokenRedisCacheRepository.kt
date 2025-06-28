package com.kamcci.numberbox.infra.redis.adapter.repository.token

import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames
import com.kamcci.numberbox.infra.redis.adapter.config.RedisConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRedisCacheRepository {
    @CacheEvict(
        cacheManager = RedisConfig.REDIS_2WEEK_CACHE_MANAGER_BEAN,
        cacheNames = [CacheNames.REFRESH_TOKEN],
        key = "#token"
    )
    fun evictCache(token: String) {
        // 캐시 삭제용 메서드
    }
}