package com.kamcci.numberbox.infra.redis.adapter.repository.token

import org.junit.jupiter.api.Test

class RefreshTokenRedisCacheRepositoryTest {
    @Test
    fun `무의미 테스트`() {
        RefreshTokenRedisCacheRepository().evictCache("")
    }
}